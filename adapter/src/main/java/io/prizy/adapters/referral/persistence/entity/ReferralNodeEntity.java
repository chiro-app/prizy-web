package io.prizy.adapters.referral.persistence.entity;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;


@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ReferralNode")
@Table(name = "referral_nodes")
public class ReferralNodeEntity {
  @Id
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "referrer_id")
  private UUID referrerId;
  @Column
  private Boolean confirmed;
  @Column
  private String code;
}
