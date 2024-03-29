package io.prizy.domain.user.port;

import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.user.model.Address;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:04 PM
 */


public interface AddressRepository {
  Optional<Address> byId(UUID id);

  Optional<Address> byUserId(UUID userId);

  Address save(Address address);
}
