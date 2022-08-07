package io.prizy.domain.user.mapper;

import io.prizy.domain.user.model.Address;
import io.prizy.domain.user.model.UpdateAddress;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 8/6/2022 10:49 PM
 */


@UtilityClass
public class UpdateAddressMapper {

  public Address map(UpdateAddress update) {
    return Address.builder()
      .id(update.id())
      .street(update.street())
      .postalCode(update.postalCode())
      .country(update.country())
      .city(update.city())
      .extraLine1(update.extraLine1())
      .extraLine2(update.extraLine2())
      .build();
  }

}
