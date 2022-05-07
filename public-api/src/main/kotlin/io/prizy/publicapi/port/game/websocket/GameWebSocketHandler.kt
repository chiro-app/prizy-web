package io.prizy.publicapi.port.game.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.prizy.domain.game.service.GameEngine
import io.prizy.publicapi.port.game.mapper.GameEventDtoMapper
import io.prizy.publicapi.port.game.websocket.dto.GameEventDto
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.getBean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:27 PM
 */

@Component
class GameWebSocketHandler(private val beanFactory: BeanFactory) : WebSocketHandler {

  private val objectMapper: ObjectMapper = jsonMapper {
    addModule(KotlinModule.Builder().build())
  }

  override fun handle(session: WebSocketSession): Mono<Void> {
    val gameEngine = beanFactory.getBean<GameEngine>() // create a new engine on each connection
    val inputFlux = session
      .receive()
      .map { message -> message.payloadAsText }
      .map { json -> objectMapper.readValue(json, GameEventDto::class.java) }
      .map { dto -> GameEventDtoMapper.map(dto) }
    val outputFlux = gameEngine
      .startGame(inputFlux)
      .map { gameEvent -> GameEventDtoMapper.map(gameEvent) }
      .map { dto -> session.textMessage(objectMapper.writeValueAsString(dto)) }
    return session.send(outputFlux)
  }
}