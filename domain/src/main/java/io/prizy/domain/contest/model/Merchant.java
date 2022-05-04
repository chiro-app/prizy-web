package io.prizy.domain.contest.model;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 01/03/2021 21:18
 */

@Builder
public record Merchant(
  String name,
  String siren,
  Float capital,
  String address,
  String logoAssetId
) {
}
