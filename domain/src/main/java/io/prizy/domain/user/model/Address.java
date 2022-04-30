package io.prizy.domain.user.model;

import java.util.Optional;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:00 PM
 */


public record Address(
  String street,
  String country,
  String city,
  String postalCode,
  Optional<String> extraLine1,
  Optional<String> extraLine2
) {
}
