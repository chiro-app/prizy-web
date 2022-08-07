package io.prizy.domain.user.model;

import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 8/6/2022 10:46 PM
 */


@Builder
public record UpdateAddress(
  UUID id,
  UUID userId,
  String street,
  String country,
  String city,
  String postalCode,
  Optional<String> extraLine1,
  Optional<String> extraLine2
) {
}
