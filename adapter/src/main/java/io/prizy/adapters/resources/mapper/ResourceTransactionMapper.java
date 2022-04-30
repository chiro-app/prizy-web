package io.prizy.adapters.resources.mapper;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.TransactionType;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 4/29/2022 8:43 PM
 */


@UtilityClass
public class ResourceTransactionMapper {

  public <T extends ResourceTransaction> ResourceTransactionEntity map(T transaction) {
    var builder = switch (transaction) {
      case ResourceTransaction.Absolute ignored -> ResourceTransactionEntity.Absolute.builder();
      case ResourceTransaction.ContestDependent contestDependent -> ResourceTransactionEntity.ContestDependent.builder()
        .contestId(contestDependent.contestId());
      default -> throw new IllegalArgumentException("Unknown transaction type + " + transaction.getClass().getSimpleName());
    };
    return builder
      .id(transaction.id())
      .currency(transaction.currency())
      .type(transaction.type())
      .amount(transaction.amount())
      .dateTime(transaction.dateTime())
      .userId(transaction.userId())
      .build();
  }

  public ResourceTransaction map(ResourceTransactionEntity entity) {
    return switch(entity) {
      case ResourceTransactionEntity.Absolute absolute -> new ResourceTransaction.Absolute(
        absolute.getId(),
        absolute.getCurrency(),
        absolute.getType(),
        absolute.getAmount(),
        absolute.getUserId(),
        absolute.getDateTime()
      );
      case ResourceTransactionEntity.ContestDependent contestDependent -> new ResourceTransaction.ContestDependent(
        contestDependent.getId(),
        contestDependent.getCurrency(),
        contestDependent.getType(),
        contestDependent.getAmount(),
        contestDependent.getUserId(),
        contestDependent.getContestId(),
        contestDependent.getDateTime()
      );
      default -> throw new IllegalArgumentException("Unknown transaction type + " + entity.getClass().getSimpleName());
    };
  }

}
