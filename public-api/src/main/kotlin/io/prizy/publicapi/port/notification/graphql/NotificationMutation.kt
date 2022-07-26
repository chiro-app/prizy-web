package io.prizy.publicapi.port.notification.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.auth.model.Roles
import io.prizy.domain.notification.event.SendPushNotification
import io.prizy.domain.notification.model.PushNotification
import io.prizy.domain.notification.publisher.NotificationPublisher
import io.prizy.graphql.directives.AuthorizedDirective
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 12:40
 */

@Component
class NotificationMutation(val notificationPublisher: NotificationPublisher) : Mutation {

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun sendPushNotification(userId: UUID, heading: String, content: String): Boolean =
    withContext(Dispatchers.IO) {
      notificationPublisher.publishPushNotification(
        SendPushNotification(
          PushNotification.SingleUser.builder()
            .userId(userId)
            .subject(heading)
            .content(content)
            .build()
        )
      )
      true
    }

  @AuthorizedDirective(roles = [Roles.ADMIN])
  suspend fun sendPushNotificationToAll(heading: String, content: String): Boolean =
    withContext(Dispatchers.IO) {
      notificationPublisher.publishPushNotification(
        SendPushNotification(
          PushNotification.AllUsers.builder()
            .subject(heading)
            .content(content)
            .build()
        )
      )
      true
    }
}