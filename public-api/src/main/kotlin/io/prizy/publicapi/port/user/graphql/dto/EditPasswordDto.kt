package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 11:55 AM
 */

@GraphQLName("EditPassword")
data class EditPasswordDto(
  val oldPassword: String,
  val newPassword: String
)
