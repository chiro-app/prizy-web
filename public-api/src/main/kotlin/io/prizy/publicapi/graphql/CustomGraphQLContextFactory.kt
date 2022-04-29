package io.prizy.publicapi.graphql

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.UUID


@Component
class CustomGraphQLContextFactory : SpringGraphQLContextFactory<GraphQLContext>() {

  companion object {
    private val log = LoggerFactory.getLogger(CustomGraphQLContextFactory::class.java)
  }

  override suspend fun generateContext(request: ServerRequest): GraphQLContext {
    return when (val context = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull()) {
      null -> GraphQLContext.Anonymous(request)
      else -> when (val jwtToken = (context.authentication as? JwtAuthenticationToken)) {
        null -> {
          log.warn("Unexpected authentication type: ${context.authentication.javaClass.name}")
          GraphQLContext.Anonymous(request)
        }
        else -> GraphQLContext.Authenticated(request, UUID.fromString(jwtToken.token.subject))
      }
    }
  }
}