package io.prizy.domain.contest.ports

import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:55 AM
 */

interface ReferralRepository {
  suspend fun getUserReferrals(userId: UUID): List<UUID>
}
