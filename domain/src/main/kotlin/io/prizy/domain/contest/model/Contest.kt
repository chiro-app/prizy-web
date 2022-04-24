package io.prizy.domain.contest.model

import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:14 AM
 */

data class Contest(
  val id: UUID,
  val name: String,
  val mediaIds: Collection<String>,
  val coverMediaId: String,
  val description: String,
  val category: ContestCategory,
  val fromDate: Instant,
  val toDate: Instant,
  val cost: Int,
  val facebookPage: String?,
  val instagramPage: String?,
  val website: String?,
  val newsletterSubscription: Boolean,
  val adultOnly: Boolean,
  val packs: Collection<Pack>,
  val merchant: Merchant,
  val boardId: UUID
)
