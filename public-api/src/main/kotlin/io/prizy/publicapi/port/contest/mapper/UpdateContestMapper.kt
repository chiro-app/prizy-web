package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.command.UpdateContest
import io.prizy.domain.contest.model.ContestCategory
import io.prizy.publicapi.port.contest.graphql.dto.ContestCategoryDto
import io.prizy.publicapi.port.contest.graphql.dto.UpdateContestDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:47 PM
 */

object UpdateContestMapper {

  fun map(dto: UpdateContestDto): UpdateContest = UpdateContest(
    id = dto.id,
    name = dto.name,
    description = dto.description,
    category = map(dto.category),
    fromDate = dto.fromDate,
    toDate = dto.toDate,
    mediaIds = dto.mediaIds,
    coverMediaId = dto.coverMediaId,
    packs = dto.packs.map(CreatePackMapper::map),
    cost = dto.cost,
    facebookPage = dto.facebookPage,
    instagramPage = dto.instagramPage,
    website = dto.website,
    newsletterSubscription = dto.newsletterSubscription,
    adultOnly = dto.adultOnly,
    merchant = MerchantDtoMapper.map(dto.merchant),
    boardId = dto.boardId,
  )

  fun map(dto: ContestCategoryDto): ContestCategory = ContestCategory.valueOf(dto.name)
}
