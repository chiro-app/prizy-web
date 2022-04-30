package io.prizy.adapters.user.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author Nidhal Dogga
 * @created 13/10/2020 13:39
 */


@Entity(name = "Device")
@Table(name = "devices")
public class DeviceEntity {
  @Id
  @GeneratedValue
  private UUID id;
  @Column(name = "device_id")
  private String deviceId;
  @Column(name = "user_id", insertable = false, updatable = false)
  private UUID userId;
  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;
}