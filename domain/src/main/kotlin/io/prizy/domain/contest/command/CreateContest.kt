package io.prizy.domain.contest.command

import io.prizy.domain.contest.model.ContestCategory
import io.prizy.domain.contest.model.Merchant
import java.time.Instant
import java.util.UUID

data class CreateContest(
  val name: String,
  val description: String,
  val category: ContestCategory,
  val fromDate: Instant,
  val toDate: Instant,
  val mediaIds: Collection<String>,
  val coverMediaId: String,
  val packs: Collection<CreatePack>,
  val cost: Int,
  val facebookPage: String?,
  val instagramPage: String?,
  val website: String?,
  val newsletterSubscription: Boolean,
  val adultOnly: Boolean,
  val merchant: Merchant,
  val boardId: UUID
)
