package io.prizy.publicapi.application.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 12:06 AM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.app.version")
data class AppConfigurationProperties(
  val usageConditionsUrl: String,
  val minimumAppVersion: String,
  val latestAppVersion: String,
  val privacyPolicyUrl: String,
  val legalNoticeUrl: String
)
