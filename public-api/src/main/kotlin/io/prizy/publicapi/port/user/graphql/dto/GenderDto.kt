package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:46 PM
 */

@GraphQLName("Gender")
enum class GenderDto {
  MALE,
  FEMALE,
  OTHER
}