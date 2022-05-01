package io.prizy.domain.user.model;

import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 12:40 PM
 */


@Builder
public record ConfirmationCode(
  String code,
  UUID userId
) {
}
