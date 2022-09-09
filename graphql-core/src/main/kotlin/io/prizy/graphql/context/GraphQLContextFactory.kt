package io.prizy.graphql.context

import com.expediagroup.graphql.server.spring.execution.DefaultSpringGraphQLContextFactory
import io.prizy.graphql.auth.Authorizations
import io.prizy.graphql.auth.JwtClaimExtractor
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class GraphQLContextFactory : DefaultSpringGraphQLContextFactory() {

  companion object {
    private val log = LoggerFactory.getLogger(GraphQLContextFactory::class.java)
  }

  override suspend fun generateContextMap(request: ServerRequest): Map<*, Any> = super.generateContextMap(request) +
    when (val context = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull()) {
      null -> mapOf()
      else -> when (val jwtToken = (context.authentication as? JwtAuthenticationToken)) {
        null -> {
          log.warn("Unexpected authentication type: ${context.authentication.javaClass.name}")
          mapOf()
        }

        else -> mapOf(
          PRINCIPAL_CONTEXT_PATH to JwtClaimExtractor.getPrincipal(jwtToken.token),
          AUTHORIZATIONS_CONTEXT_PATH to Authorizations(
            roles = JwtClaimExtractor.getRoles(jwtToken.token),
            permissions = JwtClaimExtractor.getPermissions(jwtToken.token)
          )
        )
      }
    }
}