package io.prizy.adapters.contest.persistence.entity;


import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity(name = "ContestSubscription")
@Table(name = "contest_subscriptions")
public class ContestSubscriptionEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contest_id")
  private ContestEntity contest;
  @Column(name = "user_id", insertable = false, updatable = false)
  private UUID userId;
  @Column(name = "contest_id", insertable = false, updatable = false)
  private UUID contestId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @Column(name = "date_time")
  private Instant dateTime;

}
