package io.prizy.graphql.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * @author Nidhal Dogga
 * @created 01/06/2022 18:13
 */

@ConstructorBinding
@ConfigurationProperties("prizy.logging")
data class LoggingProperties(
  val logToElasticSearch: Boolean = false,
  val indexName: String = ""
)
