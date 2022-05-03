package io.prizy.publicapi.port.asset.rest

import io.prizy.domain.asset.service.AssetService
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.exception.AssetNotFoundException
import io.prizy.publicapi.port.asset.mapper.AssetDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI
import java.util.Optional

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 13:46
 */

@RestController
@RequestMapping("/assets")
class AssetController(private val assetService: AssetService) {

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  suspend fun uploadFile(@RequestPart("file") filePart: FilePart): AssetDto = withContext(Dispatchers.IO) {
    assetService.upload(filePart).let(AssetDtoMapper::map)
  }

  @GetMapping("/{assetId}")
  fun getAsset(@PathVariable assetId: String, response: ServerHttpResponse): Mono<Void> = response
    .apply {
      when (val downloadUrl = assetService.getDownloadUrl(assetId)) {
        Optional.empty<String>() -> {
          statusCode = HttpStatus.NOT_FOUND
          throw AssetNotFoundException()
        }
        else -> {
          statusCode = HttpStatus.PERMANENT_REDIRECT
          headers.location = URI.create(downloadUrl.get())
        }
      }
    }
    .setComplete()
}