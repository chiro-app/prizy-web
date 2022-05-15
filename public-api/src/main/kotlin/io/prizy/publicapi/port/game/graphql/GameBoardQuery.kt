package io.prizy.publicapi.port.game.graphql

import com.expediagroup.graphql.server.operations.Query
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.game.service.GameBoardService
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.game.graphql.dto.GameBoardDto
import io.prizy.publicapi.port.game.mapper.GameBoardDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:11
 */

@Component
class GameBoardQuery(private val gameBoardService: GameBoardService) : Query {

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun gameBoards(): List<GameBoardDto> = withContext(Dispatchers.IO) {
    gameBoardService.list().map(GameBoardDtoMapper::map)
  }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun gameBoard(id: UUID): GameBoardDto? = withContext(Dispatchers.IO) {
    gameBoardService
      .byId(id)
      .map(GameBoardDtoMapper::map)
      .orElse(null)
  }
}