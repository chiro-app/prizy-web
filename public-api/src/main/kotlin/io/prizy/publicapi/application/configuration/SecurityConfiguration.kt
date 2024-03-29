package io.prizy.publicapi.application.configuration

import io.prizy.publicapi.application.properties.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import reactor.core.publisher.Mono

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:45 PM
 */

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfiguration : WebFluxConfigurer {

  override fun addCorsMappings(registry: CorsRegistry) {
    registry
      .addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("*")
      .allowedHeaders("*")
      .maxAge(3600)
  }

  @Bean
  internal fun securityWebFilterChain(
    http: ServerHttpSecurity,
    jwtDecoder: ReactiveJwtDecoder
  ): SecurityWebFilterChain = http
    .authorizeExchange()
    .anyExchange().permitAll() // Let GraphQL decide
    .and()
    .csrf().disable()
    .cors().disable()
    .formLogin().disable()
    .httpBasic().disable()
    .authenticationManager { authentication -> Mono.just(authentication) }
    .oauth2ResourceServer { oauth2 ->
      oauth2
        .jwt()
        .jwtDecoder(jwtDecoder)
        .and()
        .accessDeniedHandler { exchange, exception ->
          val bytes = """
            {
              "errors":[
                {
                  "extensions": {
                    "errorCode": "FORBIDDEN"
                  }
                }
              ]
            }
          """.trimIndent().toByteArray(Charsets.UTF_8)
          val buffer = exchange.response.bufferFactory().wrap(bytes)
          exchange.response.headers.contentType = MediaType.APPLICATION_JSON
          exchange.response.statusCode = HttpStatus.FORBIDDEN
          exchange.response.writeWith(Mono.just(buffer))
        }
        .authenticationEntryPoint { exchange, _ ->
          val bytes = """
            {
              "errors":[
                {
                  "extensions": {
                    "errorCode": "UNAUTHORIZED"
                  }
                }
              ]
            }
          """.trimIndent().toByteArray(Charsets.UTF_8)
          val buffer = exchange.response.bufferFactory().wrap(bytes)
          exchange.response.headers.contentType = MediaType.APPLICATION_JSON
          exchange.response.statusCode = HttpStatus.UNAUTHORIZED
          exchange.response.writeWith(Mono.just(buffer))
        }
    }
    .build()

  @Bean
  internal fun jwtDecoder(properties: JwtProperties): ReactiveJwtDecoder = NimbusReactiveJwtDecoder
    .withPublicKey(properties.rsaPublicKey)
    .build()
    .apply { setJwtValidator(JwtValidators.createDefaultWithIssuer(properties.issuer)) }
}
