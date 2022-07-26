package io.prizy.publicapi.application.properties

import io.prizy.adapters.notification.properties.PushNotificationProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 12:00
 */

@ConstructorBinding
@ConfigurationProperties(prefix = "prizy.push")
data class PushNotificationProperties(
  val apiUrl: String,
  val apiKey: String,
  val appId: String
) {

  val toDomain: PushNotificationProperties
    get() = PushNotificationProperties(apiUrl, apiKey, appId)
}
