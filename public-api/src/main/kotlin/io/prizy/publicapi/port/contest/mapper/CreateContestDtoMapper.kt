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

object CreateContestDtoMapper {

  fun map(dto: CreateContestDto): CreateContest = CreateContest.builder()
    .name(dto.name)
    .description(dto.description)
    .category(map(dto.category))
    .fromDate(dto.fromDate)
    .toDate(dto.toDate)
    .assetIds(dto.assetIds)
    .coverMediaId(dto.coverMediaId)
    .packs(dto.packs.map(CreatePackDtoMapper::map))
    .cost(dto.cost)
    .facebookPage(Optional.ofNullable(dto.facebookPage))
    .instagramPage(Optional.ofNullable(dto.instagramPage))
    .website(Optional.ofNullable(dto.website))
    .newsletterSubscription(dto.newsletterSubscription)
    .adultOnly(dto.adultOnly)
    .merchant(MerchantDtoMapper.map(dto.merchant))
    .boardId(dto.boardId)
    .build()

  fun map(dto: ContestCategoryDto): ContestCategory = ContestCategory.valueOf(dto.name)
}
