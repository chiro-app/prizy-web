package io.prizy.domain.contest.ports

import io.prizy.domain.contest.model.Contest
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:20 AM
 */

interface ContestRepository {
  suspend fun byId(contestId: UUID): Contest?
  suspend fun list(): Collection<Contest>
  suspend fun create(contest: Contest): Contest
  suspend fun update(contest: Contest): Contest
  suspend fun delete(contestId: UUID)
}
