package io.prizy.domain.resources.usecase;

import java.util.UUID;

import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.exception.ResourceBonusAlreadyClaimedException;
import io.prizy.domain.resources.ports.ResourcePublisher;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.properties.ResourceProperties;
import io.prizy.domain.resources.service.ResourceBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.prizy.domain.resources.model.TransactionType.BONUS;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 09:37
 */


@Component
@RequiredArgsConstructor
public class ClaimKeysBonusUseCase {

  private final ResourceBonusService bonusService;
  private final ResourcePublisher resourcePublisher;
  private final ResourceProperties resourceProperties;
  private final ResourceRepository resourceRepository;

  public void claim(UUID userId) {
    if (!bonusService.hasAvailableKeysBonus(userId)) {
      throw new ResourceBonusAlreadyClaimedException();
    }
    var transaction = resourceRepository.alterKeys(userId, resourceProperties.dailyKeysBonus().longValue(), BONUS);
    resourcePublisher.publish(new ResourceTransactionCreated(transaction));
  }

}
