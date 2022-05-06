package io.prizy.publicapi.application.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy
import org.springframework.web.reactive.socket.server.WebSocketService
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 9:32 PM
 */

@Configuration
class WebSocketConfiguration {

  @Bean
  fun webSocketHandler(handler: WebSocketHandler): HandlerMapping = SimpleUrlHandlerMapping(mapOf("/ws" to handler), 1)

  @Bean
  fun webSocketService(upgradeStrategy: RequestUpgradeStrategy): WebSocketService =
    HandshakeWebSocketService(upgradeStrategy)

  @Bean
  fun handlerAdapter(webSocketService: WebSocketService) = WebSocketHandlerAdapter(webSocketService)
}