package io.prizy.adapters.asset.mapper;

import io.prizy.adapters.asset.persistence.entity.AssetEntity;
import io.prizy.domain.asset.model.Asset;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 10:27
 */

@UtilityClass
public class AssetMapper {

  public AssetEntity map(Asset asset) {
    return AssetEntity.builder()
      .id(asset.id())
      .path(asset.path())
      .size(asset.size())
      .type(asset.type())
      .originalName(asset.originalName())
      .build();
  }

  public Asset map(AssetEntity entity) {
    return Asset.builder()
      .id(entity.getId())
      .path(entity.getPath())
      .size(entity.getSize())
      .type(entity.getType())
      .originalName(entity.getOriginalName())
      .build();
  }

}
