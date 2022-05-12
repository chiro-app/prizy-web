package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.service.AssetService
import io.prizy.domain.contest.service.ContestService
import io.prizy.domain.contest.service.ContestSubscriptionService
import io.prizy.domain.referral.service.ReferralService
import io.prizy.domain.reward.service.RewardService
import io.prizy.domain.user.service.AddressService
import io.prizy.domain.user.service.UserPreferencesService
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import io.prizy.publicapi.port.contest.graphql.dto.ContestDto
import io.prizy.publicapi.port.contest.mapper.ContestDtoMapper
import io.prizy.publicapi.port.contest.mapper.ReferralDtoMapper
import io.prizy.publicapi.port.referral.graphql.dto.ReferralNodeDto
import io.prizy.publicapi.port.reward.graphql.dto.RewardDto
import io.prizy.publicapi.port.reward.mapper.RewardDtoMapper
import io.prizy.publicapi.port.user.mapper.AddressDtoMapper
import io.prizy.publicapi.port.user.mapper.UserPreferencesDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:47 PM
 */

@GraphQLName("User")
data class UserDto(
  val id: UUID,
  val username: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val gender: GenderDto,
  val birthDate: LocalDate,
  @GraphQLIgnore
  val avatarAssetId: String?,
  val mobilePhone: String?,
  val status: UserStatusDto,
  val created: Instant,
  @GraphQLIgnore
  val addressId: UUID?
) {

  suspend fun rewards(@GraphQLIgnore @Autowired rewardService: RewardService): List<RewardDto> =
    withContext(Dispatchers.IO) {
      rewardService.userRewards(id).map(RewardDtoMapper::map)
    }

  suspend fun preferences(@GraphQLIgnore @Autowired userPreferencesService: UserPreferencesService): UserPreferencesDto =
    withContext(Dispatchers.IO) {
      UserPreferencesDtoMapper.map(userPreferencesService.forUser(id))
    }

  suspend fun avatar(@GraphQLIgnore @Autowired assetService: AssetService): AssetDto? = withContext(Dispatchers.IO) {
    avatarAssetId?.let(assetService::get)?.map(AssetDtoMapper::map)?.get()
  }

  suspend fun address(@GraphQLIgnore @Autowired addressService: AddressService): AddressDto? =
    withContext(Dispatchers.IO) {
      addressId
        ?.let(addressService::getAddress)
        ?.let(AddressDtoMapper::map)
    }

  suspend fun referralCode(@GraphQLIgnore @Autowired referralService: ReferralService): String =
    withContext(Dispatchers.IO) {
      referralService.referralCode(id)
    }

  suspend fun referrer(@GraphQLIgnore @Autowired referralService: ReferralService): ReferralNodeDto? =
    withContext(Dispatchers.IO) {
      referralService.getReferrer(id).map(ReferralDtoMapper::map).orElse(null)
    }

  suspend fun referrals(@GraphQLIgnore @Autowired referralService: ReferralService): List<ReferralNodeDto> =
    withContext(Dispatchers.IO) {
      referralService.getReferrals(id).map(ReferralDtoMapper::map)
    }

  suspend fun subscriptions(
    @GraphQLIgnore @Autowired subscriptionService: ContestSubscriptionService,
    @GraphQLIgnore @Autowired contestService: ContestService
  ): List<ContestDto> =
    withContext(Dispatchers.IO) {
      subscriptionService
        .ofUser(id)
        .map { subscription -> subscription.contestId }
        .let { contestIds -> contestService.byIds(contestIds) }
        .map(ContestDtoMapper::map)
    }
}