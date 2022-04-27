package io.prizy.adapters.referral.persistence.entity;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.prizy.adapters.user.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ReferralNode")
@Table(name = "referral_nodes")
public class ReferralNodeEntity {
  @Id
  @GeneratedValue
  private UUID id;
  @Column(name = "user_id", insertable = false, updatable = false)
  private UUID userId;
  @Column(name = "referrer_id", insertable = false, updatable = false)
  private UUID referrerId;
  @Column
  private Boolean confirmed;
  @Column
  private String code;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "referrer_id")
  private ReferralNodeEntity referrer;
}
