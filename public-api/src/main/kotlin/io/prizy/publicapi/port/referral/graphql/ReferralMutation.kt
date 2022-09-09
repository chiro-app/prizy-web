package io.prizy.publicapi.port.referral.graphql

import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.referral.usecase.SubmitReferralCodeUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ReferralMutation(private val submitReferralCodeUseCase: SubmitReferralCodeUseCase) : Mutation {

  @AuthorizedDirective
  suspend fun submitReferrer(dfe: DataFetchingEnvironment, code: String): Boolean = withContext(Dispatchers.IO) {
    submitReferralCodeUseCase.submit(dfe.principal().id, code)
  }

  @AuthorizedDirective
  @Deprecated("To be removed, referral confirmation is now automatic")
  suspend fun confirmReferral(dfe: DataFetchingEnvironment, referralId: UUID): Boolean =
    withContext(Dispatchers.IO) {
      true
    }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun submitReferrerById(id: UUID, code: String): Boolean = withContext(Dispatchers.IO) {
    submitReferralCodeUseCase.submit(id, code)
  }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  @Deprecated("To be removed, referral confirmation is now automatic")
  suspend fun confirmReferralById(id: UUID, referralId: UUID): Boolean = withContext(Dispatchers.IO) {
    true
  }
}