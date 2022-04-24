package io.prizy.adapters.contest.persistence

import io.prizy.adapters.contest.mapper.ContestMapper
import io.prizy.adapters.contest.persistence.repository.ContestJpaRepository
import io.prizy.domain.contest.model.Contest
import io.prizy.domain.contest.ports.ContestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:12 PM
 */

@Component
class ContestRepositoryImpl(private val jpaRepository: ContestJpaRepository) : ContestRepository {

  override suspend fun byId(contestId: UUID): Contest? = withContext(Dispatchers.IO) {
    jpaRepository
      .findByIdOrNull(contestId)
      ?.let(ContestMapper::map)
  }

  override suspend fun list(): Collection<Contest> = withContext(Dispatchers.IO) {
    jpaRepository
      .findAll()
      .map(ContestMapper::map)
  }

  override suspend fun update(contest: Contest): Contest = withContext(Dispatchers.IO) {
    jpaRepository
      .save(ContestMapper.map(contest)
        .also { contestEntity ->
          contestEntity.packs.forEach { pack ->
            pack.contest = contestEntity
          }
        })
      .let(ContestMapper::map)
  }

  override suspend fun create(contest: Contest): Contest = update(contest)

  override suspend fun delete(contestId: UUID) = withContext(Dispatchers.IO) {
    jpaRepository.deleteById(contestId)
  }
}
