package io.prizy.adapters.resources.persistence;

import java.util.UUID;

import io.prizy.adapters.resources.mapper.ResourceTransactionMapper;
import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.adapters.resources.persistence.repository.AbsoluteResourceTransactionJpaRepository;
import io.prizy.domain.resources.model.ResourceTransaction;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepository {

  private final AbsoluteResourceTransactionJpaRepository absoluteTransactionJpaRepository;

  @Override
  public Long countKeys(UUID userId) {
    return absoluteTransactionJpaRepository
      .findAbsolutesByCurrency(Currency.KEYS)
      .stream()
      .reduce(
        0L,
        (acc, transaction) -> transaction.getType().getTransactionFunction().apply(acc, transaction.getAmount()),
        Long::sum
      );
  }

  @Override
  public ResourceTransaction saveTransaction(ResourceTransaction transaction) {
    var entity = (ResourceTransactionEntity.Absolute) ResourceTransactionMapper.map(transaction);
    entity = absoluteTransactionJpaRepository.save(entity);
    return ResourceTransactionMapper.map(entity);
  }

}
