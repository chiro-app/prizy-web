package io.prizy.publicapi.port.resource.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 12:15
 */

@GraphQLName("Balance")
data class ResourceBalanceDto(
  val lives: Int,
  val diamonds: Long
)
