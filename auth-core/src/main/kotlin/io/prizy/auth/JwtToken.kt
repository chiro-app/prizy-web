package io.prizy.auth

import java.time.Instant

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 8:42 PM
 */

data class JwtToken(
  val token: String,
  val refreshToken: String,
  val expiresAt: Instant,
  val type: String
)
