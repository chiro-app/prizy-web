package io.prizy.adapters.resources.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity.Absolute;
import io.prizy.domain.resources.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsoluteResourceTransactionJpaRepository extends JpaRepository<Absolute, UUID> {
  Collection<Absolute> findAbsolutesByCurrencyAndUserId(Currency currency, UUID userId);
}
