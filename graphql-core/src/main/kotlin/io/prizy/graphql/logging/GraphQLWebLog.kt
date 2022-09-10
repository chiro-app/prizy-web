package io.prizy.graphql.logging

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import io.prizy.graphql.auth.Principal
import io.prizy.graphql.context.PRINCIPAL_CONTEXT_PATH
import io.prizy.graphql.context.REQUEST_CONTEST_PATH
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.remoteAddressOrNull
import java.time.Duration
import java.time.Instant

/**
 * @author Nidhal Dogga
 * @created 9/9/2022 10:23 PM
 */


data class GraphQLWebLog(
  val timestamp: Instant,
  val duration: Long,
  val principal: String?,
  val clientIp: String?,
  val query: String,
  val response: String?,
  val requestHeaders: String,
  val errorCode: String?,
  val stacktrace: String?
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
    params.graphQLContext.get<Principal?>(PRINCIPAL_CONTEXT_PATH)?.id?.toString(),
    params.graphQLContext.get<ServerRequest>(REQUEST_CONTEST_PATH).remoteAddressOrNull()?.address?.toString(),
    params.query,
    result?.getData<Any>()?.let(ObjectMapper()::writeValueAsString),
    ObjectMapper().writeValueAsString(params.graphQLContext.get<ServerRequest>(REQUEST_CONTEST_PATH).headers().asHttpHeaders()),
    result?.errors?.map { error -> error.extensions?.get("errorCode") }?.firstOrNull() as? String,
    throwable?.stackTraceToString()
  )
}