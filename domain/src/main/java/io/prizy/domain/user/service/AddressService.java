package io.prizy.domain.user.service;

import java.util.UUID;

import io.prizy.domain.user.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:02 PM
 */


@Service
@RequiredArgsConstructor
public class AddressService {

  public Address getAddress(UUID id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public Address updateUserAddress(UUID userId, Address address) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
