package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.referral.model.Referral
import io.prizy.publicapi.port.contest.graphql.dto.ReferralDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:11 PM
 */

object ReferralDtoMapper {

  fun map(referral: Referral): ReferralDto = ReferralDto(referral.id)

}
