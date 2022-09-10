package io.prizy.graphql.logging

import graphql.ExecutionResult
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import io.prizy.graphql.properties.GraphQLLoggingProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * @author Nidhal Dogga
 * @created 08/09/2022 11:05
 */

@Service
@ConditionalOnProperty("prizy.graphql.logging.enabled", havingValue = "true")
class GraphQLWebLogger(
  private val operations: ElasticsearchOperations,
  private val properties: GraphQLLoggingProperties
) {

  companion object {
    private val indexingPool = Executors.newFixedThreadPool(3)
    private val log = LoggerFactory.getLogger(GraphQLWebLogger::class.java)
  }

  fun logRequest(
    params: InstrumentationExecutionParameters,
    from: Instant,
    to: Instant,
    result: ExecutionResult?,
    throwable: Throwable?
  ) {
    CompletableFuture
      .supplyAsync({
        val query = IndexQueryBuilder()
          .withIndex(properties.index)
          .withObject(GraphQLWebLog(params, from, to, result, throwable))
          .build()
        val coordinates = IndexCoordinates.of(properties.index)
        operations.index(query, coordinates)
      }, indexingPool)
      .thenAcceptAsync { response ->
        log.trace("Successfully indexed web log with id {}", response)
      }
      .exceptionallyAsync { exception ->
        log.debug("Indexing web log failed", exception)
        return@exceptionallyAsync null
      }
  }
}