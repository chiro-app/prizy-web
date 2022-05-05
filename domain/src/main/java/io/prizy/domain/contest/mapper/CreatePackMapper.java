package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.model.CreatePack;
import io.prizy.domain.contest.model.Pack;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreatePackMapper {

  public Pack map(CreatePack create) {
    return switch (create) {
      case CreatePack.Coupon coupon -> Pack.Coupon.builder()
        .name(coupon.name())
        .firstWinnerPosition(coupon.firstWinnerPosition())
        .lastWinnerPosition(coupon.lastWinnerPosition())
        .code(coupon.code())
        .expiration(coupon.expiration())
        .build();
      case CreatePack.Product product -> Pack.Product.builder()
        .name(product.name())
        .firstWinnerPosition(product.firstWinnerPosition())
        .lastWinnerPosition(product.lastWinnerPosition())
        .quantity(product.quantity())
        .value(product.value())
        .assetId(product.assetId())
        .build();
    };
  }

}
