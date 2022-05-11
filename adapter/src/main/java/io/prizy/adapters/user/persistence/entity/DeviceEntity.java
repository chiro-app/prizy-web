package io.prizy.adapters.user.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author Nidhal Dogga
 * @created 13/10/2020 13:39
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Device")
@Table(name = "devices")
public class DeviceEntity {
  @Id
  @GeneratedValue
  private String id;
  @Column(name = "device_id")
  private String deviceId;
  @Column(name = "user_id")
  private UUID userId;
}