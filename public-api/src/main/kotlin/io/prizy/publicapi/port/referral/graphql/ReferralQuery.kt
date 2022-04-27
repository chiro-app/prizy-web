package io.prizy.publicapi.port.referral.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.referral.service.ReferralService
import io.prizy.publicapi.port.contest.graphql.dto.ReferralNodeDto
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReferralQuery(
  private val referralService: ReferralService
) : Query {

  suspend fun getUserReferrals(userId: UUID): List<ReferralNodeDto> = referralService
    .getReferrals(userId)
    .map { ReferralNodeDto(it.userId, it.confirmed) }
}