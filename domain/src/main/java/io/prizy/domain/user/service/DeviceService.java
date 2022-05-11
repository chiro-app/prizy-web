package io.prizy.domain.user.service;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.user.model.Device;
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

  public Collection<Device> getForUser(UUID userId) {
    return repository.byUserId(userId);
  }

  public Boolean register(UUID userId, String deviceId) {
    return repository.save(new Device(deviceId, userId));
  }

  public Boolean unregister(String deviceId) {
    return repository.delete(deviceId);
  }

}
