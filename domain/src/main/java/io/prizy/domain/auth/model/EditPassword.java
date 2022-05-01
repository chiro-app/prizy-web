package io.prizy.domain.auth.model;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/1/2022 11:07 AM
 */


@Builder
public record EditPassword(
  String oldPassword,
  String newPassword
) {
}
