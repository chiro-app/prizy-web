package io.prizy.adapters.contest.persistence

import io.prizy.adapters.contest.mapper.PackMapper
import io.prizy.adapters.contest.persistence.repository.PackJpaRepository
import io.prizy.domain.contest.model.Pack
import io.prizy.domain.contest.ports.PackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:59 PM
 */

@Component
class PackRepositoryImpl(
  private val jpaRepository: PackJpaRepository
) : PackRepository {

  override suspend fun byId(id: UUID): Pack? = withContext(Dispatchers.IO) {
    jpaRepository
      .findByIdOrNull(id)
      ?.let(PackMapper::map)
  }
}
