package io.prizy.domain.ranking.publisher;

import io.prizy.domain.ranking.event.RankingChanged;

/**
 * @author Nidhal Dogga
 * @created 8/8/2022 11:18 PM
 */


public interface RankingPublisher {
  void publish(RankingChanged event);
}
