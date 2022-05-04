package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.LocalDate
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 6:03 PM
 */

@GraphQLName("UpdateUser")
data class UpdateUserDto(
  val username: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val gender: GenderDto,
  val birthDate: LocalDate,
  val avatarAssetId: String?,
  val mobilePhone: String?,
  val addressId: UUID?
)
