package io.prizy.publicapi.port.referral.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.referral.service.ReferralService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReferralMutation(
  private val referralService: ReferralService
) : Mutation {

  suspend fun submitReferrer(code: String) = referralService.submitReferralCode(UUID.randomUUID(), code)

  suspend fun confirmReferral(referralId: UUID) = referralService.confirmReferralCode(UUID.randomUUID(), referralId)

  suspend fun submitReferrerById(id: UUID, code: String) = referralService.submitReferralCode(id, code)

  suspend fun confirmReferralById(id: UUID, referralId: UUID) = referralService.confirmReferralCode(id, referralId)

}