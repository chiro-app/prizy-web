package io.prizy.adapters.reward.persistence.entity;

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
 * @created 04/05/2022 14:56
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Reward")
@Table(name = "rewards")
public class RewardEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Column(name = "user_id")
  private UUID packId;
  @Column(name = "pack_id")
  private UUID userId;
  private Instant created;

}
