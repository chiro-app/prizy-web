package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.Contest
import io.prizy.domain.contest.model.ContestCategory
import io.prizy.publicapi.port.contest.graphql.dto.ContestCategoryDto
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:02 PM
 */

object ContestDtoMapper {

  fun map(contest: Contest): ContestDto {
    return ContestDto(
      id = contest.id,
      name = contest.name,
      description = contest.description,
      fromDate = contest.fromDate,
      toDate = contest.toDate,
      adultOnly = contest.adultOnly,
      category = map(contest.category),
      coverMediaId = contest.coverMediaId,
      assetIds = contest.assetIds.toList(),
      cost = contest.cost,
      facebookPage = contest.facebookPage.orElse(null),
      instagramPage = contest.instagramPage.orElse(null),
      website = contest.website.orElse(null),
      newsletterSubscription = contest.newsletterSubscription,
      merchant = MerchantDtoMapper.map(contest.merchant),
      boardId = contest.boardId,
      packs = contest.packs.map(PackDtoMapper::map)
    )
  }

  fun map(category: ContestCategory): ContestCategoryDto = ContestCategoryDto.valueOf(category.name)
}
