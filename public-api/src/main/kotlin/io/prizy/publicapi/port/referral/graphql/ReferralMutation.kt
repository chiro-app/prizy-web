package io.prizy.publicapi.port.referral.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.referral.service.ReferralService
import io.prizy.graphql.context.GraphQLContext
import io.prizy.graphql.directives.AuthorizedDirective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReferralMutation(
  private val referralService: ReferralService
) : Mutation {

  @AuthorizedDirective
  suspend fun submitReferrer(ctx: GraphQLContext.Authenticated, code: String): Boolean = withContext(Dispatchers.IO) {
    referralService.submitReferralCode(UUID.randomUUID(), code)
  }

  @AuthorizedDirective
  suspend fun confirmReferral(ctx: GraphQLContext.Authenticated, referralId: UUID): Boolean =
    withContext(Dispatchers.IO) {
      referralService.confirmReferralCode(UUID.randomUUID(), referralId)
    }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun submitReferrerById(id: UUID, code: String): Boolean = withContext(Dispatchers.IO) {
    referralService.submitReferralCode(id, code)
  }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun confirmReferralById(id: UUID, referralId: UUID): Boolean = withContext(Dispatchers.IO) {
    referralService.confirmReferralCode(id, referralId)
  }
}