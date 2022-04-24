package io.prizy.adapters.contest.mapper

import io.prizy.adapters.contest.persistence.entity.MerchantEntity
import io.prizy.domain.contest.model.Merchant

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:28 PM
 */

object MerchantMapper {

  fun map(entity: MerchantEntity): Merchant {
    return Merchant(
      name = entity.name,
      address = entity.address,
      siren = entity.siren,
      logoMediaId = entity.logoMediaId,
      capital = entity.capital,
    )
  }

  fun map(merchant: Merchant): MerchantEntity {
    return MerchantEntity(
      name = merchant.name,
      address = merchant.address,
      siren = merchant.siren,
      logoMediaId = merchant.logoMediaId,
      capital = merchant.capital,
    )
  }
}
