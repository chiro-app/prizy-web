package io.prizy.domain.contest.model;

/**
 * @author Nidhal Dogga
 * @created 01/03/2021 21:18
 */

public record Merchant(
  String name,
  String siren,
  Float capital,
  String address,
  String logoMediaId
) {
}
