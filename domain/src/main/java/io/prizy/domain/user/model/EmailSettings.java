package io.prizy.domain.user.model;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:24 PM
 */


@Builder
public record EmailSettings(
  Boolean newsletter,
  Boolean tips
) {

  public EmailSettings() {
    this(true, true);
  }

}
