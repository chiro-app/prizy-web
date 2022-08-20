package io.prizy.auth

import io.prizy.auth.properties.JwtProperties
import io.prizy.auth.websocket.AuthenticatedRequestUpgradeStrategy
import io.prizy.domain.auth.service.RefreshTokenService
import io.prizy.domain.user.port.PasswordHasher
import io.prizy.domain.user.service.UserService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy

/**
 * @author Nidhal Dogga
 * @created 8/19/2022 9:11 PM
 */

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
open class AuthCoreAutoConfiguration {

  @Bean
  open fun authProvider(
    userService: UserService,
    jwtProperties: JwtProperties,
    passwordHasher: PasswordHasher,
    refreshTokenService: RefreshTokenService
  ) = AuthProvider(userService, jwtProperties, passwordHasher, refreshTokenService)

  @Bean
  open fun requestUpgradeStrategy(decoder: ReactiveJwtDecoder): RequestUpgradeStrategy =
    AuthenticatedRequestUpgradeStrategy(decoder)
}