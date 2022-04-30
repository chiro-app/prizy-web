package io.prizy.domain.resources.ports;

import io.prizy.domain.resources.model.ResourceTransaction;

import java.util.UUID;

public interface ResourceRepository {
  Long countKeys(UUID userId);
  ResourceTransaction saveTransaction(ResourceTransaction transaction);
}