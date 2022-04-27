package io.prizy.domain.contest.mapper;


import io.prizy.domain.contest.command.CreatePack;
import io.prizy.domain.contest.model.Pack;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreatePackMapper {

  public Pack map(CreatePack command) {
    return switch (command) {
      case CreatePack.Coupon coupon -> new Pack.Coupon(
        null,
        coupon.name(),
        coupon.firstWinnerPosition(),
        coupon.lastWinnerPosition(),
        coupon.code(),
        coupon.expiration()
      );
      case CreatePack.Product product -> new Pack.Product(
        null,
        product.name(),
        product.firstWinnerPosition(),
        product.lastWinnerPosition(),
        product.quantity(),
        product.value(),
        product.mediaId()
      );
    };
  }

}
