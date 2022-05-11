package io.prizy.adapters.user.mapper;

import io.prizy.adapters.user.persistence.entity.DeviceEntity;
import io.prizy.domain.user.model.Device;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 5/10/2022 8:57 PM
 */


@UtilityClass
public class DeviceMapper {

  public Device map(DeviceEntity entity) {
    return Device.builder()
      .id(entity.getDeviceId())
      .userId(entity.getUserId())
      .build();
  }

  public DeviceEntity map(Device device) {
    return DeviceEntity.builder()
      .deviceId(device.id())
      .userId(device.userId())
      .build();
  }

}
