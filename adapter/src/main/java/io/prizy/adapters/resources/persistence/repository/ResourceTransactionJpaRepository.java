package io.prizy.adapters.resources.persistence.repository;

import java.util.UUID;

import io.prizy.adapters.resources.persistence.entity.ResourceTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceTransactionJpaRepository extends JpaRepository<ResourceTransactionEntity, UUID> {


}
