package io.prizy.adapters.user.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.user.persistence.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 5:23 PM
 */


public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, String> {
  Collection<DeviceEntity> findAllByUserId(UUID userId);

  void deleteByDeviceId(String deviceId);
}
