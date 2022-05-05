package io.prizy.adapters.contest.persistence.entity;


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
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "contest_id")
  private UUID contestId;
  @Column(name = "date_time")
  private Instant dateTime;

}
