package io.prizy.auth.websocket

import com.fasterxml.jackson.core.JacksonException
import io.netty.handler.codec.http.HttpResponseStatus
import io.prizy.toolbox.exception.AuthenticationRequiredException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.NettyDataBufferFactory
import org.springframework.http.server.reactive.ServerHttpResponseDecorator.getNativeResponse
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.reactive.socket.HandshakeInfo
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.adapter.ReactorNettyWebSocketSession
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServerResponse
import java.util.function.Supplier

/**
 *  @author Nidhal Dogga
 *  @created 5/6/2022 10:16 PM
 */

class AuthenticatedRequestUpgradeStrategy(private val jwtDecoder: ReactiveJwtDecoder) : RequestUpgradeStrategy {

  companion object {
    private val log: Logger = LoggerFactory.getLogger(AuthenticatedRequestUpgradeStrategy::class.java)
    private val BEARER_TOKEN_PARAM = "bearer_token"
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
      .onErrorResume { throwable ->
        val status = when (throwable) {
          is AuthenticationRequiredException -> HttpResponseStatus.UNAUTHORIZED
          is JacksonException -> HttpResponseStatus.BAD_REQUEST
          else -> HttpResponseStatus.INTERNAL_SERVER_ERROR
        }
        Mono.just(reactorResponse.status(status)).flatMap(HttpServerResponse::send)
      }
  }

  private fun getAuthResult(handshakeInfo: HandshakeInfo): Mono<Jwt> {
    val bearerToken = UriComponentsBuilder
      .fromUri(handshakeInfo.uri)
      .build()
      .queryParams[BEARER_TOKEN_PARAM]
      ?.firstOrNull()
      ?: return Mono.error(AuthenticationRequiredException())
    return jwtDecoder
      .decode(bearerToken)
      .doOnError { throwable -> log.warn("Error while decoding token", throwable) }
  }
}