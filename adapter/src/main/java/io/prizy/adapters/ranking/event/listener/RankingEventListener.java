package io.prizy.adapters.ranking.event.listener;

import io.prizy.domain.ranking.service.RankingService;
import io.prizy.domain.resources.event.ResourceTransactionCreated;
import io.prizy.domain.resources.model.Currency;
import io.prizy.domain.resources.model.ResourceTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Nidhal Dogga
 * @created 05/05/2022 15:23
 */

@Component
@RequiredArgsConstructor
public class RankingEventListener {

  private final RankingService rankingService;

  @TransactionalEventListener
  public void onResourceTransactionCreate(ResourceTransactionCreated event) {
    if (Currency.DIAMONDS.equals(event.transaction().currency())
      && event.transaction() instanceof ResourceTransaction.ContestDependent transaction) {
      rankingService.incrementByAndSubmitScore(transaction.contestId(), transaction.userId(),
        event.transaction().amount());
    }
  }

}
