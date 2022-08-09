package io.prizy.adapters.ranking.event.publisher;

import io.prizy.domain.ranking.event.RankingChanged;
import io.prizy.domain.ranking.publisher.RankingPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal Dogga
 * @created 8/8/2022 11:20 PM
 */


@Service
@RequiredArgsConstructor
public class SpringRankingPublisher implements RankingPublisher {

  private final ApplicationEventPublisher aep;

  @Override
  public void publish(RankingChanged event) {
    aep.publishEvent(event);
  }

}
