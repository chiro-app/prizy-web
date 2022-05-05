package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.contest.model.ContestCategory
import io.prizy.domain.contest.model.UpdateContest
import io.prizy.publicapi.port.contest.graphql.dto.ContestCategoryDto
import io.prizy.publicapi.port.contest.graphql.dto.UpdateContestDto
import java.util.Optional

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:47 PM
 */

object UpdateContestMapper {

  fun map(dto: UpdateContestDto): UpdateContest = UpdateContest.builder()
    .id(dto.id)
    .name(dto.name)
    .description(dto.description)
    .category(map(dto.category))
    .fromDate(dto.fromDate)
    .toDate(dto.toDate)
    .assetIds(dto.assetIds)
    .coverAssetId(dto.coverAssetId)
    .packs(dto.packs.map(CreatePackDtoMapper::map))
    .cost(dto.cost)
    .facebookPage(Optional.ofNullable(dto.facebookPage))
    .instagramPage(Optional.ofNullable(dto.instagramPage))
    .website(Optional.ofNullable(dto.website))
    .newsletterSubscription(dto.newsletterSubscription)
    .adultOnly(dto.adultOnly)
    .merchant(CreateMerchantDtoMapper.map(dto.merchant))
    .boardId(dto.boardId)
    .build()

  fun map(dto: ContestCategoryDto): ContestCategory = ContestCategory.valueOf(dto.name)
}
