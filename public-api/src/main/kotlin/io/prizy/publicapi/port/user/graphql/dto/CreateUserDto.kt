package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.LocalDate

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:44 PM
 */

@GraphQLName("CreateUser")
data class CreateUserDto(
  val email: String,
  val username: String,
  val password: String,
  val firstName: String,
  val lastName: String,
  val gender: GenderDto,
  val birthDate: LocalDate,
  val mobilePhone: String? = null
)
