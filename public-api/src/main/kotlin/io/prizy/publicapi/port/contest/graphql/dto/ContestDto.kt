package io.prizy.publicapi.port.contest.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.service.AssetService
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.game.service.GameBoardService
import io.prizy.domain.ranking.service.RankingService
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.application.properties.GameDescriptionProperties
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import io.prizy.publicapi.port.contest.mapper.GameDescriptionDtoMapper
import io.prizy.publicapi.port.game.graphql.dto.GameBoardDto
import io.prizy.publicapi.port.game.mapper.GameBoardDtoMapper
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
  @GraphQLIgnore
  val assetIds: List<String>,
  @GraphQLIgnore
  val coverAssetId: String,
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
  @AuthorizedDirective(roles = [Roles.ADMIN])
  val accessCode: String,
  val boardId: UUID
) {

  suspend fun assets(@GraphQLIgnore @Autowired assetService: AssetService): List<AssetDto> =
    withContext(Dispatchers.IO) {
      assetService.getMany(assetIds).map(AssetDtoMapper::map)
    }

  suspend fun cover(@GraphQLIgnore @Autowired assetService: AssetService): AssetDto = withContext(Dispatchers.IO) {
    assetService.get(coverAssetId).map(AssetDtoMapper::map).get()
  }

  suspend fun rulesHTML(@GraphQLIgnore @Autowired contestService: ContestService): String =
    withContext(Dispatchers.IO) {
      contestService.contestRules(id)
    }

  suspend fun rankingTable(@GraphQLIgnore @Autowired rankingService: RankingService): RankingTableDto =
    withContext(Dispatchers.IO) {
      rankingService.getForContest(id).let(RankingTableDtoMapper::map)
    }

  fun gameDescription(@GraphQLIgnore @Autowired gameDescriptionProperties: GameDescriptionProperties) =
    GameDescriptionDtoMapper.map(gameDescriptionProperties)

  suspend fun board(@GraphQLIgnore @Autowired gameBoardService: GameBoardService): GameBoardDto =
    withContext(Dispatchers.IO) {
      gameBoardService
        .byId(boardId)
        .map(GameBoardDtoMapper::map)
        .get()
    }
}
