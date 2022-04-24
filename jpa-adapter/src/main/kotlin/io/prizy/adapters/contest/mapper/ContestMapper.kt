package io.prizy.adapters.contest.mapper

import io.prizy.adapters.contest.persistence.entity.ContestEntity
import io.prizy.domain.contest.model.Contest

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:16 PM
 */

object ContestMapper {

  fun map(entity: ContestEntity): Contest {
    return Contest(
      id = entity.id,
      name = entity.name,
      description = entity.description,
      fromDate = entity.fromDate,
      toDate = entity.toDate,
      adultOnly = entity.adultOnly,
      category = entity.category,
      coverMediaId = entity.coverMediaId,
      mediaIds = entity.mediaIds.toList(),
      cost = entity.cost,
      facebookPage = entity.facebookPage,
      instagramPage = entity.instagramPage,
      website = entity.website,
      newsletterSubscription = entity.newsletterSubscription,
      merchant = MerchantMapper.map(entity.merchant),
      boardId = entity.boardId,
      packs = entity.packs.map(PackMapper::map)
    )
  }

  fun map(contest: Contest): ContestEntity {
    return ContestEntity(
      id = contest.id,
      name = contest.name,
      description = contest.description,
      fromDate = contest.fromDate,
      toDate = contest.toDate,
      adultOnly = contest.adultOnly,
      category = contest.category,
      coverMediaId = contest.coverMediaId,
      mediaIds = contest.mediaIds.toSet(),
      cost = contest.cost,
      facebookPage = contest.facebookPage,
      instagramPage = contest.instagramPage,
      website = contest.website,
      newsletterSubscription = contest.newsletterSubscription,
      merchant = MerchantMapper.map(contest.merchant),
      boardId = contest.boardId,
      packs = contest.packs.map(PackMapper::map).toSet()
    )
  }
}
