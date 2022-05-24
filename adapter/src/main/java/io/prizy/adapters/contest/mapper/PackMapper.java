package io.prizy.adapters.contest.mapper;

import io.prizy.adapters.contest.persistence.entity.PackEntity;
import io.prizy.domain.contest.model.Pack;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:30 PM
 */

@UtilityClass
public class PackMapper {

  public Pack map(PackEntity entity) {
    if (entity instanceof PackEntity.Product product) {
      return new Pack.Product(
        product.getId(),
        product.getName(),
        product.getLastWinnerPosition(),
        product.getFirstWinnerPosition(),
        product.getQuantity(),
        product.getValue(),
        product.getAssetId()
      );
    } else if (entity instanceof PackEntity.Coupon coupon) {
      return new Pack.Coupon(
        coupon.getId(),
        coupon.getName(),
        coupon.getLastWinnerPosition(),
        coupon.getFirstWinnerPosition(),
        coupon.getCode(),
        coupon.getExpiration()
      );
    }
    throw new IllegalArgumentException("Unknown pack type " + entity.getClass());
  }

  public PackEntity map(Pack pack) {
    if (pack instanceof Pack.Product product) {
      return PackEntity.Product.builder()
        .id(product.id())
        .name(product.name())
        .assetId(product.assetId())
        .lastWinnerPosition(product.lastWinnerPosition())
        .firstWinnerPosition(product.firstWinnerPosition())
        .quantity(product.quantity())
        .value(product.value())
        .build();
    } else if (pack instanceof Pack.Coupon coupon) {
      return PackEntity.Coupon.builder()
        .id(coupon.id())
        .name(coupon.name())
        .lastWinnerPosition(coupon.lastWinnerPosition())
        .firstWinnerPosition(coupon.firstWinnerPosition())
        .code(coupon.code())
        .expiration(coupon.expiration())
        .build();
    }
    throw new IllegalArgumentException("Unknown pack type " + pack.getClass());
  }
}
