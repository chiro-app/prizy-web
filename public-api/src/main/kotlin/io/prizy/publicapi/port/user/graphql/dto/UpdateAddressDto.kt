package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 5/1/2022 4:02 PM
 */

@GraphQLName("UpdateAddress")
data class UpdateAddressDto(
  val street: String,
  val country: String,
  val city: String,
  val postalCode: String,
  val extraLine1: String?,
  val extraLine2: String?
)