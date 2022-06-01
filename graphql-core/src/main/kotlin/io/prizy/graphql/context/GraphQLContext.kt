package io.prizy.graphql.context

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import io.prizy.graphql.auth.Authorizations
import io.prizy.graphql.auth.Principal
import org.springframework.web.reactive.function.server.ServerRequest

sealed class GraphQLContext(open val request: ServerRequest) : SpringGraphQLContext(request) {

  data class Anonymous(override val request: ServerRequest) : GraphQLContext(request)

  data class Authenticated(
    override val request: ServerRequest,
    val principal: Principal,
    val authorizations: Authorizations
  ) : GraphQLContext(request)
}