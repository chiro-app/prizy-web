package io.prizy.graphql.auth

import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 12:00 AM
 */

data class Principal(
  val id: UUID,
  val username: String?,
  val email: String?,
  val emailVerified: Boolean
)