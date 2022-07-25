package io.prizy.adapters.user.mapper;

import java.util.Optional;

import io.prizy.adapters.user.persistence.entity.AddressEntity;
import io.prizy.domain.user.model.Address;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/14/2022 6:10 PM
 */


@UtilityClass
public class AddressMapper {

  public Address map(AddressEntity entity) {
    return Address.builder()
      .street(entity.getStreet())
      .city(entity.getCity())
      .country(entity.getCountry())
      .postalCode(entity.getZipCode())
      .extraLine1(Optional.ofNullable(entity.getExtraLine1()))
      .extraLine2(Optional.ofNullable(entity.getExtraLine2()))
      .build();
  }

  public AddressEntity map(Address address) {
    return AddressEntity.builder()
      .street(address.street())
      .city(address.city())
      .country(address.country())
      .zipCode(address.postalCode())
      .extraLine1(address.extraLine1().orElse(null))
      .extraLine2(address.extraLine2().orElse(null))
      .build();
  }

}
