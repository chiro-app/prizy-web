package io.prizy.publicapi.port.game.websocket

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.prizy.domain.game.model.GameEvent
import io.prizy.domain.game.model.GameEvent.Error.Code
import io.prizy.domain.game.service.GameOrchestrator
import io.prizy.publicapi.port.game.mapper.GameEventDtoMapper
import io.prizy.publicapi.port.game.websocket.dto.GameEventDto
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:27 PM
 */

@Component
class GameWebSocketHandler(private val gameOrchestrator: GameOrchestrator) : WebSocketHandler {

  companion object {
    private val log = LoggerFactory.getLogger(GameWebSocketHandler::class.java)
  }

  private val objectMapper: ObjectMapper = jsonMapper {
    addModule(KotlinModule.Builder().build())
  }

  override fun handle(session: WebSocketSession): Mono<Void> {
    val eventFlux = session
      .receive()
      .map { message ->
        try {
          val event = message
            .payloadAsText
            .let { json -> objectMapper.readValue(json, GameEventDto::class.java) }
            .let(GameEventDtoMapper::map)
          val requestId = session.id
          val userId = runBlocking { session.handshakeInfo.principal.awaitSingle().name }
          gameOrchestrator.handleEvent(event, UUID.fromString(userId), requestId)
        } catch (throwable: Throwable) {
          log.warn("Error while handling event", throwable)
          when (throwable) {
            is JacksonException -> GameEvent.Error(Code.ILLEGAL_EVENT)
            else -> GameEvent.Error(Code.UNKNOWN)
          }
        }
      }
      .takeUntil { event -> event.isFinal }
      .map { event ->
        event
          .let(GameEventDtoMapper::map)
          .let(objectMapper::writeValueAsString)
          .let(session::textMessage)
      }
    return session.send(eventFlux)
  }
}

private operator fun <T1, T2> Tuple2<T1, T2>.component1(): T1 = t1
private operator fun <T1, T2> Tuple2<T1, T2>.component2(): T2 = t2
