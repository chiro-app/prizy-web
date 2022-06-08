package io.prizy.domain.user.usecase;

import io.prizy.domain.user.model.Address;
import io.prizy.domain.user.port.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:54
 */


@Component
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {


  private final AddressRepository addressRepository;

  @Transactional
  public Address update(Address address) {
    return addressRepository.save(addressRepository
      .byUserId(address.userId())
      .map(old -> old
        .withStreet(address.street())
        .withCity(address.city())
        .withCountry(address.country())
        .withPostalCode(address.postalCode())
        .withExtraLine1(address.extraLine1())
        .withExtraLine2(address.extraLine2())
      )
      .orElse(address)
    );
  }

}
