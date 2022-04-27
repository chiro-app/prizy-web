package io.prizy.adapters.resources.persistence.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.prizy.adapters.contest.persistence.entity.ContestEntity;
import io.prizy.adapters.user.persistence.entity.UserEntity;
import io.prizy.domain.resources.model.ResourceTransactionType;
import io.prizy.domain.resources.model.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ResourceTransaction")
@Table(name = "resource_transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public sealed class ResourceTransactionEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type")
  private ResourceType resourceType;
  @Column
  @Enumerated(EnumType.STRING)
  private ResourceTransactionType type;
  @Column
  private Long amount;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @Column(name = "date_time")
  private Instant dateTime;

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "AbsoluteResourceTransaction")
  public static non-sealed class Absolute extends ResourceTransactionEntity {

  }

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "ContestDependentResourceTransaction")
  public static non-sealed class ContestDependent extends ResourceTransactionEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private ContestEntity contest;

  }

}
