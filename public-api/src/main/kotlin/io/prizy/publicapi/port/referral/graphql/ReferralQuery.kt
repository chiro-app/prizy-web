package io.prizy.publicapi.port.referral.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.referral.service.ReferralService
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.contest.graphql.dto.ReferralNodeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReferralQuery(
  private val referralService: ReferralService
) : Query {

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun getUserReferrals(userId: UUID): List<ReferralNodeDto> = withContext(Dispatchers.IO) {
    referralService.getReferrals(userId)
  }.map { ReferralNodeDto(it.userId, it.confirmed) }
}