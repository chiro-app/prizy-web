package io.prizy.domain.contest.event;

import io.prizy.domain.contest.model.Contest;

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 1:52 AM
 */


public record ContestUpdated(
  Contest contest
) {
}