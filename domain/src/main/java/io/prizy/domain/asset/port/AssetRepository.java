package io.prizy.domain.asset.port;

import java.util.Optional;

import io.prizy.domain.asset.model.Asset;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:51 AM
 */


public interface AssetRepository {
  Asset save(Asset asset);

  Optional<Asset> byId(String id);
}
