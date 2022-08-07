package io.prizy.domain.user.usecase;

import io.prizy.domain.user.mapper.UpdateAddressMapper;
import io.prizy.domain.user.model.Address;
import io.prizy.domain.user.model.UpdateAddress;
import io.prizy.domain.user.port.AddressRepository;
import io.prizy.domain.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Nidhal Dogga
 * @created 09/06/2022 19:54
 */


@Component
@RequiredArgsConstructor
public class UpdateUserAddressUseCase {

  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

  @Transactional
  public Address update(UpdateAddress request) {
    var user = userRepository.byId(request.userId()).get();
    var address = addressRepository.save(
      addressRepository
        .byUserId(request.userId())
        .map(old -> UpdateAddressMapper
          .map(request)
          .withId(old.id())
        )
        .orElseGet(() -> UpdateAddressMapper.map(request))
    );
    user = user.withAddressId(Optional.of(address.id()));
    user = userRepository.save(user);
    return address;
  }

}
