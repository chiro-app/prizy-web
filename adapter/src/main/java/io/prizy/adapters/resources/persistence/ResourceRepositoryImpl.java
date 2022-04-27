package io.prizy.adapters.resources.persistence;

import java.util.UUID;

import io.prizy.adapters.resources.persistence.repository.AbsoluteResourceTransactionJpaRepository;
import io.prizy.domain.resources.model.ResourceType;
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
      .findAbsolutesByResourceType(ResourceType.KEYS)
      .stream()
      .reduce(
        0L,
        (acc, transaction) -> transaction.getType().getTransactionFunction().apply(acc, transaction.getAmount()),
        Long::sum
      );
  }

}
