package io.prizy.publicapi.auth

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import io.prizy.domain.auth.service.RefreshTokenService
import io.prizy.domain.user.model.User
import io.prizy.domain.user.model.UserStatus
import io.prizy.domain.user.port.PasswordHasher
import io.prizy.domain.user.service.UserService
import io.prizy.publicapi.application.properties.JwtProperties
import io.prizy.publicapi.auth.exception.InvalidCredentialsException
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 8:41 PM
 */

@Component
class AuthProvider(
  private val userService: UserService,
  private val jwtProperties: JwtProperties,
  private val passwordHasher: PasswordHasher,
  private val refreshTokenService: RefreshTokenService
) {

  suspend fun authenticate(login: String, password: String): JwtToken {
    val user = userService
      .getUserByEmailOrUsername(login)
      .orElseThrow { InvalidCredentialsException() }
    if (!passwordHasher.matches(password, user.passwordHash)) {
      throw InvalidCredentialsException()
    }
    return buildJwtToken(user)
  }

  suspend fun refreshCredentialS(refreshToken: String): JwtToken {
    val user = refreshTokenService
      .findUserId(refreshToken)
      .orElseThrow { InvalidCredentialsException() }
      .let { userId -> userService.getUser(userId).get() }
    return buildJwtToken(user)
  }

  suspend fun buildJwtToken(user: User): JwtToken {
    val claims = mapOf(
      "roles" to user.roles,
      "email" to user.email,
      "username" to user.username,
      "email_verified" to (user.status == UserStatus.CONFIRMED),
      "grant_type" to "password"
    )
    val jwt = issueJwt(user.id.toString(), claims)
    val refreshToken = refreshTokenService.getOrCreate(user.id)
    return JwtToken(
      jwt,
      refreshToken,
      getExpirationDate(jwtProperties.tokenExpiration),
      "Bearer"
    )
  }

  private fun issueJwt(subject: String, claims: Map<String, Any?>): String {
    val signer = RSASSASigner(jwtProperties.jwk)
    val claimSet = JWTClaimsSet.Builder()
      .subject(subject)
      .issuer(jwtProperties.issuer)
      .expirationTime(Date.from(getExpirationDate(jwtProperties.tokenExpiration)))
      .audience(jwtProperties.audience)
      .apply { claims.forEach { (key, value) -> claim(key, value) } }
      .build()
    val jwtHeader = JWSHeader.Builder(JWSAlgorithm.parse(jwtProperties.algorithm))
      .keyID(jwtProperties.keyId)
      .build()
    return SignedJWT(jwtHeader, claimSet)
      .also { it.sign(signer) }
      .serialize()
  }

  private fun getExpirationDate(expiresInSeconds: Long): Instant {
    return LocalDateTime.now()
      .plusSeconds(expiresInSeconds)
      .toInstant(ZoneOffset.UTC)
  }
}