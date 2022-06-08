package io.prizy.domain.user.service;

import java.util.UUID;

import io.prizy.domain.user.model.Address;
import io.prizy.domain.user.port.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:02 PM
 */


@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

  private final AddressRepository repository;

  public Address getAddress(UUID id) {
    return repository.byId(id).get();
  }

}
