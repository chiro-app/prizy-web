package io.prizy.publicapi.port.auth.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:49 PM
 */

@GraphQLName("Login")
data class LoginDto(
  val login: String,
  val password: String
)
