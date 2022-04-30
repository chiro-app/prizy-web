package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:47 PM
 */

@GraphQLName("User")
data class UserDto(
  val id: UUID,
  val username: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val gender: GenderDto,
  val birthDate: LocalDate,
  val avatarMediaId: String?,
  val mobilePhone: String?,
  val status: UserStatusDto,
  val created: Instant,
  @GraphQLIgnore
  val addressId: UUID?
)