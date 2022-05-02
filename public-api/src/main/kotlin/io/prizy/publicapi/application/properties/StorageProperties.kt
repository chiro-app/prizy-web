package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 12:21 AM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.storage")
data class StorageProperties(
  val endpoint: String,
  val bucketName: String,
  val accessKey: String,
  val secretKey: String,
  val serverUrl: String,
  val region: String?
)
