package io.prizy.graphql.context

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import io.prizy.graphql.auth.Principal
import io.prizy.graphql.auth.Authorizations
import org.springframework.web.reactive.function.server.ServerRequest

sealed class GraphQLContext(request: ServerRequest) : SpringGraphQLContext(request) {

  data class Anonymous(val request: ServerRequest) : GraphQLContext(request)

  data class Authenticated(
    val request: ServerRequest,
    val principal: Principal,
    val authorizations: Authorizations
  ) : GraphQLContext(request)
}