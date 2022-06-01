package io.prizy.graphql.server

import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.types.GraphQLRequest
import com.expediagroup.graphql.server.types.GraphQLResponse
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.GraphQL
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.logging.QueryType
import io.prizy.graphql.logging.WebLog
import io.prizy.graphql.properties.LoggingProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.remoteAddressOrNull
import java.time.Instant
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 01/06/2022 17:48
 */

@Component
@ConditionalOnProperty("prizy.logging.log-to-elastic-search", havingValue = "true", matchIfMissing = false)
class LoggingGraphQLRequestHandler(
  graphQL: GraphQL,
  private val objectMapper: ObjectMapper,
  private val properties: LoggingProperties,
  private val elasticsearchClient: ReactiveElasticsearchClient
) : GraphQLRequestHandler(graphQL) {

  companion object {
    private val log = LoggerFactory.getLogger(LoggingGraphQLRequestHandler::class.java)
  }

  override suspend fun executeRequest(
    request: GraphQLRequest,
    context: com.expediagroup.graphql.generator.execution.GraphQLContext?,
    graphQLContext: Map<*, Any>?
  ): GraphQLResponse<*> {
    val from = Instant.now()
    val response = super.executeRequest(request, context, graphQLContext)
    val to = Instant.now()
    val elapsed = to.toEpochMilli() - from.toEpochMilli()
    if (!skipRequest(request)) {
      log.info(
        "Request {} finished in {}ms",
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request),
        elapsed
      )
      indexRequest(request, response, (context as GraphQLContext).request, elapsed)
    }
    return response
  }

  private suspend fun indexRequest(
    request: GraphQLRequest,
    response: GraphQLResponse<*>,
    serverRequest: ServerRequest,
    elapsed: Long
  ) = try {
    val webLog = WebLog(
      elapsed = elapsed,
      query = request.query,
      response = response.data,
      timestamp = Instant.now(),
      variables = request.variables,
      type = extractQueryType(request.query),
      ip = serverRequest.remoteAddressOrNull()?.toString(),
      headers = serverRequest.headers().asHttpHeaders().toSingleValueMap(),
    )
    val indexRequest = IndexRequest(properties.indexName)
      .id(UUID.randomUUID().toString())
      .source(webLog, XContentType.JSON)
    val indexResponse = elasticsearchClient.index(indexRequest).awaitSingle()
    log.trace("received web log response {}", indexResponse)
  } catch (throwable: Throwable) {
    log.warn("Request indexing failed, {}", throwable.message, throwable)
  }

  private fun extractQueryType(query: String): QueryType = when {
    query.contains("mutation") -> QueryType.MUTATION
    else -> QueryType.QUERY
  }

  private fun skipRequest(request: GraphQLRequest): Boolean = request.query
    .lowercase()
    .contains("query introspectionquery")
}