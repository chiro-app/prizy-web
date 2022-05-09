package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.model.CreatePack;
import io.prizy.domain.contest.model.Pack;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreatePackMapper {

  public Pack map(CreatePack create) {
    if (create instanceof CreatePack.Coupon coupon) {
      return Pack.Coupon.builder()
        .name(coupon.name())
        .firstWinnerPosition(coupon.firstWinnerPosition())
        .lastWinnerPosition(coupon.lastWinnerPosition())
        .code(coupon.code())
        .expiration(coupon.expiration())
        .build();
    }
    if (create instanceof CreatePack.Product product) {
      Pack.Product.builder()
        .name(product.name())
        .firstWinnerPosition(product.firstWinnerPosition())
        .lastWinnerPosition(product.lastWinnerPosition())
        .quantity(product.quantity())
        .value(product.value())
        .assetId(product.assetId())
        .build();
    }
    return null;
  }

}
