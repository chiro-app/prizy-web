package io.prizy.domain.resources.service

import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:34 AM
 */

open class ResourceService {

  suspend fun getKeyCount(userId: UUID): Int {
    TODO("not implemented")
  }

  suspend fun withdrawKeys(userId: UUID, count: Int) {
    TODO("not implemented")
  }
}
