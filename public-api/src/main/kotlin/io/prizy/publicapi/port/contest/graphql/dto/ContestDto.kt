package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.ranking.service.RankingService
import io.prizy.publicapi.port.ranking.graphql.dto.RankingTableDto
import io.prizy.publicapi.port.ranking.mapper.RankingTableDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 8:37 PM
 */

@GraphQLName("Contest")
data class ContestDto(
  val id: UUID,
  val name: String,
  val assetIds: List<String>,
  val coverMediaId: String,
  val description: String,
  val category: ContestCategoryDto,
  val fromDate: Instant,
  val toDate: Instant,
  val cost: Int,
  val facebookPage: String?,
  val instagramPage: String?,
  val website: String?,
  val newsletterSubscription: Boolean,
  val adultOnly: Boolean,
  val packs: List<PackDto>,
  val merchant: MerchantDto,
  val boardId: UUID
) {

  suspend fun rulesHTML(@GraphQLIgnore @Autowired contestService: ContestService): String =
    withContext(Dispatchers.IO) {
      contestService.contestRules(id)
    }

  suspend fun rankingTable(@GraphQLIgnore @Autowired rankingService: RankingService): RankingTableDto =
    withContext(Dispatchers.IO) {
      rankingService.getForContest(id).let(RankingTableDtoMapper::map)
    }
}
