package io.prizy.adapters.user.persistence;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.user.mapper.DeviceMapper;
import io.prizy.adapters.user.persistence.repository.DeviceJpaRepository;
import io.prizy.domain.user.model.Device;
import io.prizy.domain.user.port.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 11:22 AM
 */


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {

  private final DeviceJpaRepository jpaRepository;

  @Override
  public Collection<Device> byUserId(UUID userId) {
    return jpaRepository
      .findAllByUserId(userId)
      .stream()
      .map(DeviceMapper::map)
      .toList();
  }

  @Override
  public Boolean save(Device device) {
    try {
      jpaRepository.save(DeviceMapper.map(device));
      return true;
    } catch (Exception e) {
      log.warn("Failed to save device {}", device, e);
      return false;
    }
  }

  @Override
  public Boolean delete(String deviceId) {
    try {
      jpaRepository.deleteByDeviceId(deviceId);
      return true;
    } catch (Exception e) {
      log.warn("Failed to delete device {}", deviceId, e);
      return false;
    }
  }
}
