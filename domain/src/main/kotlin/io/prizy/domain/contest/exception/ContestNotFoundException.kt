package io.prizy.domain.contest.exception

import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 12:42 AM
 */

data class ContestNotFoundException(val id: UUID) : Throwable()
