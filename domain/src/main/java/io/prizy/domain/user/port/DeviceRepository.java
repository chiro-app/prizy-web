package io.prizy.domain.user.port;

import java.util.Collection;
import java.util.UUID;

import io.prizy.domain.user.model.Device;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 3:04 PM
 */


public interface DeviceRepository {
  Collection<Device> byUserId(UUID userId);

  Boolean save(Device device);

  Boolean delete(String deviceId);
}
