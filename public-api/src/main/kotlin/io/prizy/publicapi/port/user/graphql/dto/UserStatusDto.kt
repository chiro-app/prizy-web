package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:59 PM
 */

@GraphQLName("UserStatus")
enum class UserStatusDto {
  PENDING,
  CONFIRMED
}