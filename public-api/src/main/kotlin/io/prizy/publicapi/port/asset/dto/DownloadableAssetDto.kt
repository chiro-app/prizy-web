package io.prizy.publicapi.port.asset.dto

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 13:50
 */

data class DownloadableAssetDto(
  val id: String,
  val originalName: String,
  val type: String,
  val size: Long,
  val downloadUrl: String
)
