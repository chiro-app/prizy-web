package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:42 PM
 */

@GraphQLName("CreateContest")
data class CreateContestDto(
  val name: String,
  val description: String,
  val category: ContestCategoryDto,
  val fromDate: Instant,
  val toDate: Instant,
  val assetIds: List<String>,
  val coverAssetId: String,
  val packs: List<CreatePackDto>,
  val cost: Int,
  val facebookPage: String?,
  val instagramPage: String?,
  val website: String?,
  val newsletterSubscription: Boolean,
  val adultOnly: Boolean,
  val merchant: CreateMerchantDto,
  val boardId: UUID
)
