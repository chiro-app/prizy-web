package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.resources.model.ResourceBalance;
import io.prizy.domain.resources.ports.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 10:17
 */


@Component
@RequiredArgsConstructor
public class GetKeysBalanceUseCase {

  private final ResourceRepository resourceRepository;

  public ResourceBalance.Absolute get(UUID userId) {
    return ResourceBalance.Absolute.builder()
      .userId(userId)
      .keys(resourceRepository.countKeys(userId))
      .build();
  }

}
