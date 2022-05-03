package io.prizy.domain.user.model;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:24 PM
 */


@Builder
public record NotificationSettings(

  Boolean leagueDowngrade,
  Boolean packWinning,
  Boolean newContests,
  Boolean couponExpiration,
  Boolean collectTickets,
  Boolean collectDiamonds
) {

  public NotificationSettings() {
    this(true, true, true, true, false, true);
  }

}
