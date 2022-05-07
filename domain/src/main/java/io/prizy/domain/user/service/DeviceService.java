package io.prizy.domain.user.service;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.user.port.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:00 PM
 */


@Service
@RequiredArgsConstructor
public class DeviceService {

  private final DeviceRepository repository;

  public Collection<String> getForUser(UUID userId) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void register(UUID userId, String deviceId) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void unregister(UUID userId, String deviceId) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
