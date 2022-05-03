package io.prizy.domain.asset.model;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:48 AM
 */


@Builder
public record Asset(
  String id,
  String path,
  String originalName,
  String type,
  Long size
) {
}
