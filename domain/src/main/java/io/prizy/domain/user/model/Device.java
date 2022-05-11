package io.prizy.domain.user.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/10/2022 8:52 PM
 */


@Builder
public record Device(
  String id,
  UUID userId
) {
}
