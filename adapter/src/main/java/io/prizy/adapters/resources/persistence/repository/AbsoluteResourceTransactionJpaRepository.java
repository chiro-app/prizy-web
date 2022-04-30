package io.prizy.adapters.resources.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import io.prizy.domain.resources.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsoluteResourceTransactionJpaRepository extends JpaRepository<ResourceTransactionEntity.Absolute, UUID> {
  Collection<ResourceTransactionEntity.Absolute> findAbsolutesByCurrency(Currency currency);
}
