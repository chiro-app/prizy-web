package io.prizy.adapters.notification.push.model;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record CreateOneSignalNotification(
  @JsonProperty("app_id")
  String appId,
  Map<OneSignalLocale, String> contents,
  Map<OneSignalLocale, String> headings,
  @JsonProperty("include_player_ids")
  Collection<UUID> deviceIds,
  @JsonProperty("included_segments")
  Collection<String> segments,
  Optional<UUID> templateId,
  @JsonProperty("data")
  Optional<Map<String, Object>> variables,
  @JsonProperty("android_accent_color")
  String androidAccentColor
) {

  public CreateOneSignalNotification {
    androidAccentColor = "FF872FFF";
  }

}