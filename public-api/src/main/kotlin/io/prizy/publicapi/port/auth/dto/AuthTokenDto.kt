package io.prizy.publicapi.port.auth.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:47 PM
 */

@GraphQLName("AuthToken")
data class AuthTokenDto(
  val token: String,
  val refreshToken: String,
  val expiresAt: Instant,
  val type: String
)
