package io.prizy.domain.user.model;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:00 PM
 */


@With
@Builder
public record Address(
  UUID userId,
  String street,
  String country,
  String city,
  String postalCode,
  Optional<String> extraLine1,
  Optional<String> extraLine2
) {
}
