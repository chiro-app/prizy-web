package io.prizy.publicapi.application

import com.nimbusds.jose.jwk.RSAKey
import io.prizy.publicapi.application.SecurityConfiguration.SecurityProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:45 PM
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfiguration {

  @Bean
  internal fun securityWebFilterChain(
    http: ServerHttpSecurity,
    jwtDecoder: ReactiveJwtDecoder
  ): SecurityWebFilterChain = http
    .authorizeExchange()
    .anyExchange().permitAll() // Let GraphQL decide
    .and()
    .csrf().disable()
    .formLogin().disable()
    .httpBasic().disable()
    .authenticationManager { authentication -> Mono.just(authentication) }
    .oauth2ResourceServer { oauth2 ->
      oauth2
        .jwt()
        .jwtDecoder(jwtDecoder)
        .and()
        .accessDeniedHandler { exchange, exception ->
          // TODO(Nidhal): Move to a configurable bean
          val bytes = """
            {"errors":[{"extensions":{"errorCode":"FORBIDDEN"}}]}
          """.trimIndent().toByteArray(Charsets.UTF_8)
          val buffer = exchange.response.bufferFactory().wrap(bytes)
          exchange.response.statusCode = HttpStatus.OK
          exchange.response.writeWith(Mono.just(buffer))
        }
        .authenticationEntryPoint { exchange, _ ->
          // TODO(Nidhal): Move to a configurable bean
          val bytes = """
            {"errors":[{"extensions":{"errorCode":"UNAUTHORIZED"}}]}
          """.trimIndent().toByteArray(Charsets.UTF_8)
          val buffer = exchange.response.bufferFactory().wrap(bytes)
          exchange.response.statusCode = HttpStatus.OK
          exchange.response.writeWith(Mono.just(buffer))
        }
    }
    .build()

  @Bean
  internal fun jwtDecoder(properties: SecurityProperties): ReactiveJwtDecoder = NimbusReactiveJwtDecoder
    .withPublicKey(properties.rsaPublicKey)
    .build()
    .apply { setJwtValidator(JwtValidators.createDefaultWithIssuer(properties.issuer)) }

  @ConstructorBinding
  @ConfigurationProperties("prizy.security")
  data class SecurityProperties(
    val issuer: String,
    val algorithm: String,
    val publicKey: String,
    val privateKey: String
  ) {

    val rsaPublicKey: RSAPublicKey
      get() = RSAKey.parseFromPEMEncodedObjects(publicKey).toRSAKey().toRSAPublicKey()

    val rsaPrivateKey: RSAPrivateKey
      get() = RSAKey.parseFromPEMEncodedObjects(privateKey).toRSAKey().toRSAPrivateKey()

  }
}
