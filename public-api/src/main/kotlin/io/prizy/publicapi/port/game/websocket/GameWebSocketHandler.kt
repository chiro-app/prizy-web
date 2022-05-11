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
    val eventFlux = session
      .receive()
      .map { message ->
        message
          .payloadAsText
          .let { json -> objectMapper.readValue(json, GameEventDto::class.java) }
          .let(GameEventDtoMapper::map)
      }
      .zipWith(session.handshakeInfo.principal)
      .map { tuple ->
        gameOrchestrator
          .handleEvent(tuple.t1, UUID.fromString(tuple.t2.name), "")
          .let(GameEventDtoMapper::map)
          .let(objectMapper::writeValueAsString)
          .let(session::textMessage)
      }
    return session.send(eventFlux)
  }
}