package io.prizy.publicapi.graphql

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID

sealed class GraphQLContext(request: ServerRequest) : SpringGraphQLContext(request) {

  data class Anonymous(val request: ServerRequest) : GraphQLContext(request)

  data class Authenticated(
    val request: ServerRequest,
    val userId: UUID,
  ) : GraphQLContext(request)
}