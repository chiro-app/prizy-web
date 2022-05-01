package io.prizy.domain.notification.event;

import io.prizy.domain.notification.model.EmailNotification;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:18 AM
 */


public record SendEmail(
  EmailNotification email
) {
}
