package io.prizy.adapters.notification.push.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 10:52
 */

public enum OneSignalLocale {
  @JsonProperty("fr")
  FRENCH,
  @JsonProperty("en")
  ENGLISH
}
