package io.prizy.adapters.user.persistence;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.user.mapper.AddressMapper;
import io.prizy.adapters.user.persistence.repository.AddressJpaRepository;
import io.prizy.adapters.user.persistence.repository.UserJpaRepository;
import io.prizy.domain.user.model.Address;
import io.prizy.domain.user.port.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/14/2022 6:08 PM
 */


@Component
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

  private final AddressJpaRepository jpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Override
  public Optional<Address> byId(UUID id) {
    return jpaRepository
      .findById(id)
      .map(AddressMapper::map);
  }

  @Override
  public Optional<Address> byUserId(UUID userId) {
    return jpaRepository
      .findByUserId(userId)
      .map(AddressMapper::map);
  }

  @Override
  public Address save(Address address) {
    var entity = AddressMapper.map(address);
    var userEntity = userJpaRepository
      .findById(address.userId())
      .map(user -> user
        .withAddress(entity.withUser(user))
      )
      .get();
    userJpaRepository.save(userEntity);
    return AddressMapper.map(entity);
  }

}
