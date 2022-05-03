package io.prizy.publicapi.port.asset.dto

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import io.prizy.domain.asset.service.AssetService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 13:50
 */

@GraphQLName("Asset")
data class AssetDto(
  val id: String,
  val originalName: String,
  val type: String,
  val size: Long
) {

  suspend fun downloadUrl(@GraphQLIgnore @Autowired assetService: AssetService): String = withContext(Dispatchers.IO) {
    assetService.getDownloadUrl(id).get()
  }
}
