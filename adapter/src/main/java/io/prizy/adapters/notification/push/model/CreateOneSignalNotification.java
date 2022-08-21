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
  @JsonProperty("included_segments")
  Collection<String> segments,
  @JsonProperty("data")
  Optional<Map<String, Object>> variables,
  @JsonProperty("android_accent_color")
  String androidAccentColor,
  @JsonProperty("include_external_user_ids")
  Collection<UUID> userIds,
  @JsonProperty("channel_for_external_user_ids")
  String channel,
  @JsonProperty("large_icon")
  String androidLargeIcon
) {

  public CreateOneSignalNotification {
    androidAccentColor = "FF872FFF";
    channel = "push";
    androidLargeIcon = "ic_onesignal_large";
  }

}