package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("CreateMerchant")
data class CreateMerchantDto(
  val name: String,
  val siren: String,
  val capital: Double,
  val address: String,
  val logoAssetId: String
)
