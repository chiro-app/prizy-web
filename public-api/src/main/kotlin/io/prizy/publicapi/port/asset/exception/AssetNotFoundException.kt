package io.prizy.publicapi.port.asset.exception

import io.prizy.toolbox.exception.ErrorCode

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 13:54
 */

@ErrorCode("ASSET_NOT_FOUND")
class AssetNotFoundException : RuntimeException()