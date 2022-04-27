package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.command.CreateContest
import io.prizy.domain.contest.model.ContestCategory
import io.prizy.publicapi.port.contest.graphql.dto.ContestCategoryDto
import io.prizy.publicapi.port.contest.graphql.dto.CreateContestDto
import java.util.Optional

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:47 PM
 */

object CreateContestMapper {

  fun map(dto: CreateContestDto): CreateContest = CreateContest(
    dto.name,
    dto.description,
    map(dto.category),
    dto.fromDate,
    dto.toDate,
    dto.mediaIds,
    dto.coverMediaId,
    dto.packs.map(CreatePackMapper::map),
    dto.cost,
    Optional.ofNullable(dto.facebookPage),
    Optional.ofNullable(dto.instagramPage),
    Optional.ofNullable(dto.website),
    dto.newsletterSubscription,
    dto.adultOnly,
    MerchantDtoMapper.map(dto.merchant),
    dto.boardId,
  )

  fun map(dto: ContestCategoryDto): ContestCategory = ContestCategory.valueOf(dto.name)
}
