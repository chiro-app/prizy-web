package io.prizy.publicapi.port.game.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.prizy.domain.game.service.GameEngine
import io.prizy.publicapi.port.game.mapper.GameEventDtoMapper
import io.prizy.publicapi.port.game.websocket.dto.GameEventDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:27 PM
 */

@Component
class WebFluxWebSocketHandler(
  private val gameEngine: GameEngine
) : WebSocketHandler {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(WebFluxWebSocketHandler::class.java)
  }

  private val objectMapper: ObjectMapper = jsonMapper {
    addModule(KotlinModule.Builder().build())
  }

  override fun handle(session: WebSocketSession): Mono<Void> {
    val inputFlux = session
      .receive()
      .map { message -> message.payloadAsText }
      .doOnEach { gameEvent -> log.debug("Received event: $gameEvent") }
      .map { json -> objectMapper.readValue(json, GameEventDto::class.java) }
      .map { dto -> GameEventDtoMapper.map(dto) }
    val outputFlux = gameEngine
      .startGame(inputFlux)
      .doOnEach { event -> log.debug("Produced event: $event") }
      .map { event -> objectMapper.writeValueAsString(event) }
      .map { json -> session.textMessage(json) }
    return session.send(outputFlux)
  }
}