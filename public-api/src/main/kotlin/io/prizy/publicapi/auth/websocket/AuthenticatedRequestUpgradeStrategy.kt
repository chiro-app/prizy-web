package io.prizy.publicapi.auth.websocket

import io.prizy.graphql.exception.AuthenticationRequiredException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.NettyDataBufferFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpResponseDecorator.getNativeResponse
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.HandshakeInfo
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServerResponse
import java.util.function.Supplier

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 10:16 PM
 */

@Component
class AuthenticatedRequestUpgradeStrategy(private val jwtDecoder: ReactiveJwtDecoder) : RequestUpgradeStrategy {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(AuthenticatedRequestUpgradeStrategy::class.java)
  }

  override fun upgrade(
    exchange: ServerWebExchange,
    webSocketHandler: WebSocketHandler,
    subProtocol: String?,
    handshakeInfoFactory: Supplier<HandshakeInfo>
  ): Mono<Void> {
    val response = exchange.response
    val reactorResponse: HttpServerResponse = getNativeResponse(response)
    val handshakeInfo = handshakeInfoFactory.get()
    val bufferFactory = response.bufferFactory() as NettyDataBufferFactory
    return getAuthResult(handshakeInfo)
      .flatMap { jwt ->
        reactorResponse.sendWebsocket { inbound, outbound ->
          val authenticatedHandshake = HandshakeInfo(
            handshakeInfo.uri,
            handshakeInfo.headers,
            Mono.just(JwtAuthenticationToken(jwt)),
            subProtocol
          )
          val session = ReactorNettyWebSocketSession(inbound, outbound, authenticatedHandshake, bufferFactory)
          webSocketHandler.handle(session)
        }
      }
  }

  private fun getAuthResult(handshakeInfo: HandshakeInfo): Mono<Jwt> {
    val bearerToken = handshakeInfo
      .headers[HttpHeaders.AUTHORIZATION]
      ?.firstOrNull()
      ?.replace("Bearer ", "")
      ?: return Mono.error(AuthenticationRequiredException())
    return jwtDecoder
      .decode(bearerToken)
      .doOnError { throwable -> log.warn("Error while decoding token", throwable) }
  }
}