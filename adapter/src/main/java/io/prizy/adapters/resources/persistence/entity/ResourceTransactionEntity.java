package io.prizy.adapters.resources.persistence.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.TransactionType;
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
public abstract class ResourceTransactionEntity {

  @Id
  @GeneratedValue
  private UUID id;
  @Enumerated(EnumType.STRING)
  @Column
  private Currency currency;
  @Column
  @Enumerated(EnumType.STRING)
  private TransactionType type;
  @Column
  private Long amount;
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "date_time")
  private Instant dateTime;

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "AbsoluteResourceTransaction")
  public static class Absolute extends ResourceTransactionEntity {

  }

  @Getter
  @SuperBuilder
  @NoArgsConstructor
  @Entity(name = "ContestDependentResourceTransaction")
  public static class ContestDependent extends ResourceTransactionEntity {

    @Column(name = "contest_id")
    private UUID contestId;

  }

}
