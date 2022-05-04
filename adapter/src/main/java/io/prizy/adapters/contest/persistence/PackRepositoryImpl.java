package io.prizy.adapters.contest.persistence;

import java.util.Optional;
import java.util.UUID;

import io.prizy.adapters.contest.mapper.PackMapper;
import io.prizy.adapters.contest.persistence.repository.PackJpaRepository;
import io.prizy.domain.contest.model.Pack;
import io.prizy.domain.contest.ports.PackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 4/24/2022 3:59 PM
 */

@Component
@RequiredArgsConstructor
public class PackRepositoryImpl implements PackRepository {

  private final PackJpaRepository jpaRepository;

  public Optional<Pack> byId(UUID id) {
    return jpaRepository
      .findById(id)
      .map(PackMapper::map);
  }
}
