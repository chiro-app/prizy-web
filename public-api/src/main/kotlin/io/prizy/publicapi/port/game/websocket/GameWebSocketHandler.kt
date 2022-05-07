package io.prizy.publicapi.port.game.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.prizy.domain.game.service.GameOrchestrator
import io.prizy.publicapi.port.game.mapper.GameEventDtoMapper
import io.prizy.publicapi.port.game.websocket.dto.GameEventDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:27 PM
 */

@Component
class GameWebSocketHandler(private val gameOrchestrator: GameOrchestrator) : WebSocketHandler {

  private val objectMapper: ObjectMapper = jsonMapper {
    addModule(KotlinModule.Builder().build())
  }

  override fun handle(session: WebSocketSession): Mono<Void> {
    val inputFlux = session
      .receive()
      .map { message -> message.payloadAsText }
      .map { json ->
        objectMapper
          .readValue(json, GameEventDto::class.java)
          .let(GameEventDtoMapper::map)
      }
    val outputFlux = session.handshakeInfo
      .principal
      .map { ctx -> UUID.fromString(ctx.name) }
      .flux()
      .flatMap { userId ->
        gameOrchestrator
          .startGame(inputFlux, userId)
          .map { gameEvent -> GameEventDtoMapper.map(gameEvent) }
          .map { dto -> session.textMessage(objectMapper.writeValueAsString(dto)) }
      }
      
    return session.send(outputFlux)
  }
}