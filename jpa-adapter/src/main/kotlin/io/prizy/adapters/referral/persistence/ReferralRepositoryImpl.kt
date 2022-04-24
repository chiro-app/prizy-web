package io.prizy.adapters.referral.persistence

import io.prizy.domain.contest.ports.ReferralRepository
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 4:07 PM
 */

@Component
class ReferralRepositoryImpl : ReferralRepository {

  override suspend fun getUserReferrals(userId: UUID): List<UUID> {
    TODO("Not yet implemented")
  }
}
