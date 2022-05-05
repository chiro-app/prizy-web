package io.prizy.publicapi.port.game.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.game.service.GameBoardService
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.game.graphql.dto.CreateGameBoardDto
import io.prizy.publicapi.port.game.graphql.dto.GameBoardDto
import io.prizy.publicapi.port.game.graphql.dto.UpdateGameBoardDto
import io.prizy.publicapi.port.game.mapper.CreateGameBoardDtoMapper
import io.prizy.publicapi.port.game.mapper.GameBoardDtoMapper
import io.prizy.publicapi.port.game.mapper.UpdateGameBoardDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 13:11
 */

@Component
class GameBoardMutation(private val gameBoardService: GameBoardService) : Mutation {

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun createGameBoard(gameBoard: CreateGameBoardDto): GameBoardDto = withContext(Dispatchers.IO) {
    gameBoardService.create(gameBoard.let(CreateGameBoardDtoMapper::map)).let(GameBoardDtoMapper::map)
  }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun updateGameBoard(gameBoard: UpdateGameBoardDto): GameBoardDto = withContext(Dispatchers.IO) {
    gameBoardService.update(gameBoard.let(UpdateGameBoardDtoMapper::map)).let(GameBoardDtoMapper::map)
  }
}