package io.prizy.domain.user.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:53 PM
 */


@Builder
public record UpdateUser(
  UUID id,
  String username,
  String email,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  Optional<String> avatarMediaId,
  Optional<String> mobilePhone
) {
}
