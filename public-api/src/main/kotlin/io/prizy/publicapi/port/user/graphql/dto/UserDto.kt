package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.referral.service.ReferralService
import io.prizy.domain.user.service.AddressService
import io.prizy.publicapi.port.contest.mapper.ReferralDtoMapper
import io.prizy.publicapi.port.referral.graphql.dto.ReferralNodeDto
import io.prizy.publicapi.port.user.mapper.AddressDtoMapper
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
  val avatarMediaId: String?,
  val mobilePhone: String?,
  val status: UserStatusDto,
  val created: Instant,
  @GraphQLIgnore val addressId: UUID?
) {

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
}