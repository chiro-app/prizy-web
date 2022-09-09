package io.prizy.graphql.logging

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import graphql.ExecutionResult
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import io.prizy.graphql.auth.Principal
import io.prizy.graphql.context.PRINCIPAL_CONTEXT_PATH
import io.prizy.graphql.properties.GraphQLLoggingProperties
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * @author Nidhal Dogga
 * @created 08/09/2022 11:05
 */

@Service
@ConditionalOnProperty("prizy.graphql.logging.enabled", havingValue = "true")
class GraphQLWebLogger(private val properties: GraphQLLoggingProperties) {

  companion object {
    private val indexingPool = Executors.newFixedThreadPool(3)
    private val log = LoggerFactory.getLogger(GraphQLWebLogger::class.java)
  }

  private val client = HttpHost(properties.host, properties.port, properties.scheme)
    .let { host ->
      RestClient
        .builder(host)
        .setHttpClientConfigCallback { clientBuilder ->
          clientBuilder
            .disableAuthCaching()
            .setDefaultCredentialsProvider(
              BasicCredentialsProvider().apply {
                setCredentials(AuthScope.ANY, UsernamePasswordCredentials(properties.username, properties.password))
              }
            )
        }
        .build()
    }
    .let { lowLevelClient -> ElasticsearchClient(RestClientTransport(lowLevelClient, JacksonJsonpMapper())) }

  fun logRequest(
    params: InstrumentationExecutionParameters,
    from: Instant,
    to: Instant,
    result: ExecutionResult?,
    throwable: Throwable?
  ) {
    CompletableFuture
      .supplyAsync({
        client.index { builder ->
          builder
            .index(properties.index)
            .document(GraphQLWebLog(params, from, to, result, throwable))
        }
      }, indexingPool)
      .thenAcceptAsync { response ->
        log.trace("Successfully indexed web log {}", response.result())
      }
      .exceptionallyAsync { exception ->
        log.warn("Indexing web log failed", exception)
        return@exceptionallyAsync null
      }
  }

  data class GraphQLWebLog(
    val timestamp: Instant,
    val duration: Long,
    val principal: Principal?,
    val query: String,
    val result: ExecutionResult?,
    val exception: Throwable?
  ) {

    constructor(
      params: InstrumentationExecutionParameters,
      from: Instant,
      to: Instant,
      result: ExecutionResult?,
      throwable: Throwable?
    ) : this(
      from,
      Duration.between(from, to).toMillis(),
      params.graphQLContext[PRINCIPAL_CONTEXT_PATH],
      params.query,
      result,
      throwable
    )
  }
}