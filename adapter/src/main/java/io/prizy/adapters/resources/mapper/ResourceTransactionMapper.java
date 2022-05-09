package io.prizy.adapters.resources.mapper;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity.ResourceTransactionEntityBuilder;
import io.prizy.domain.resources.model.ResourceTransaction;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 4/29/2022 8:43 PM
 */

@UtilityClass
public class ResourceTransactionMapper {

  public <T extends ResourceTransaction> ResourceTransactionEntity map(T transaction) {
    ResourceTransactionEntityBuilder<?, ?> builder;
    if (transaction instanceof ResourceTransaction.Absolute) {
      builder = ResourceTransactionEntity.Absolute.builder();
    } else if (transaction instanceof ResourceTransaction.ContestDependent contestDependent) {
      builder = ResourceTransactionEntity.ContestDependent.builder()
        .contestId(contestDependent.contestId());
    } else {
      throw new IllegalArgumentException("Unknown transaction type + " + transaction.getClass().getSimpleName());
    }
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
    if (entity instanceof ResourceTransactionEntity.Absolute absolute) {
      return new ResourceTransaction.Absolute(
        absolute.getId(),
        absolute.getCurrency(),
        absolute.getType(),
        absolute.getAmount(),
        absolute.getUserId(),
        absolute.getDateTime()
      );
    } else if (entity instanceof ResourceTransactionEntity.ContestDependent contestDependent) {
      return new ResourceTransaction.ContestDependent(
        contestDependent.getId(),
        contestDependent.getCurrency(),
        contestDependent.getType(),
        contestDependent.getAmount(),
        contestDependent.getUserId(),
        contestDependent.getContestId(),
        contestDependent.getDateTime()
      );
    }
    throw new IllegalArgumentException("Unknown transaction type + " + entity.getClass().getSimpleName());
  }

}
