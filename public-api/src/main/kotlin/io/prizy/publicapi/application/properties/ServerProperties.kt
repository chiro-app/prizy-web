package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/26/2022 2:35 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.server")
data class ServerProperties(
  val url: String
)
