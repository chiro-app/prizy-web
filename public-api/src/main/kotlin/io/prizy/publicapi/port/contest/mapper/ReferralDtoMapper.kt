package io.prizy.publicapi.port.contest.mapper

import io.prizy.domain.referral.model.ReferralNode
import io.prizy.publicapi.port.referral.graphql.dto.ReferralNodeDto

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 10:11 PM
 */

object ReferralDtoMapper {

  fun map(referralNode: ReferralNode): ReferralNodeDto = ReferralNodeDto(
    referralNode.userId,
    referralNode.confirmed,
  )
}
