package io.prizy.domain.user.model;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 2:53 PM
 */


@Builder
public record CreateUser(
  String username,
  String email,
  String password,
  String firstName,
  String lastName,
  Gender gender,
  LocalDate birthDate,
  Optional<String> mobilePhone
) {
}
