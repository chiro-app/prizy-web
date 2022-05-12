package io.prizy.publicapi.port.resource.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant

/**
 *  @author Nidhal Dogga
 *  @created 5/12/2022 8:17 PM
 */

@GraphQLName("ResourceBonusStatus")
data class ResourceBonusStatusDto(
  val available: Boolean,
  val availableAt: Instant?
)
