package io.prizy.adapters.resources.persistence.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceTransactionJpaRepository extends JpaRepository<ResourceTransactionEntity, UUID> {
  Collection<ResourceTransactionEntity> findAllByUserIdAndTypeAndCurrencyAndDateTimeBetween(UUID userId,
                                                                                            TransactionType type,
                                                                                            Currency currency,
                                                                                            Instant from,
                                                                                            Instant to);

  Optional<ResourceTransactionEntity> findTop1ByUserIdOrderByDateTimeDesc(UUID userId);
}
