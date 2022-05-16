package io.prizy.adapters.resources.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity.ContestDependent;
import io.prizy.domain.resources.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestDependentResourceTransactionJpaRepository extends JpaRepository<ContestDependent, UUID> {
  Collection<ContestDependent> findContestDependentsByCurrencyAndContestIdAndUserId(Currency currency, UUID contestId
    , UUID userId);

}
