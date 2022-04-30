package io.prizy.domain.user.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.With;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:53 PM
 */


@With
@Builder
public record User(
  UUID id,
  String username,
  String email,
  String passwordHash,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  Optional<String> avatarMediaId,
  Optional<String> mobilePhone,
  UserStatus status,
  Instant created,
  Optional<UUID> addressId
) {
}
