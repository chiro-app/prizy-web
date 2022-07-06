package io.prizy.domain.notification.model;

import java.util.Map;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:09 AM
 */


@Builder
public record EmailNotification(
  UUID userId,
  String subject,
  String templateName,
  Map<String, Object> data
) {
}
