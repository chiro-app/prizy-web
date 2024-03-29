package io.prizy.publicapi.port.asset.mapper

import io.prizy.domain.asset.model.Asset
import io.prizy.publicapi.port.asset.dto.AssetDto
import io.prizy.publicapi.port.asset.dto.DownloadableAssetDto

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 13:52
 */

object AssetDtoMapper {

  fun map(asset: Asset): AssetDto = AssetDto(
    id = asset.id,
    originalName = asset.originalName,
    type = asset.type,
    size = asset.size
  )

  fun map(asset: Asset, downloadUrl: String) = DownloadableAssetDto(
    id = asset.id,
    originalName = asset.originalName,
    type = asset.type,
    size = asset.size,
    downloadUrl = downloadUrl
  )
}