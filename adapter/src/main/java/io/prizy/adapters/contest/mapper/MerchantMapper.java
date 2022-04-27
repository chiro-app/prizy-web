package io.prizy.adapters.contest.mapper;

import io.prizy.adapters.contest.persistence.entity.MerchantEntity;
import io.prizy.domain.contest.model.Merchant;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 1:28 PM
 */

@UtilityClass
public class MerchantMapper {

  public Merchant map(MerchantEntity entity) {
    return new Merchant(
      entity.getName(),
      entity.getSiren(),
      entity.getCapital(),
      entity.getAddress(),
      entity.getLogoMediaId()
    );
  }

  public MerchantEntity map(Merchant merchant) {
    return MerchantEntity.builder()
      .name(merchant.name())
      .siren(merchant.siren())
      .capital(merchant.capital())
      .address(merchant.address())
      .logoMediaId(merchant.logoMediaId())
      .build();
  }

}
