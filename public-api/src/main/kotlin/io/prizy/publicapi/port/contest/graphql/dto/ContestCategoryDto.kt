package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:38 PM
 */

@Suppress("unused")
@GraphQLName("ContestCategory")
enum class ContestCategoryDto {
  FOOD_DRINKS,
  HIGH_TECH,
  SPORT,
  HOME,
  PURSES_BAGS,
  TOILETRY,
  GAMES_TOYS,
  FASHION,
  SHOES
}
