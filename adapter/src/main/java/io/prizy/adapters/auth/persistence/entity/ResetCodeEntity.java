package io.prizy.adapters.auth.persistence.entity;

import java.time.Instant;
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
 * @created 5/1/2022 11:36 AM
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ResetCode")
@Table(name = "reset_codes")
public class ResetCodeEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true)
  private String code;

  @Column(name = "user_id")
  private UUID userId;

  @Column
  private Instant created;

}
