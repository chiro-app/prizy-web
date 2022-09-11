package io.prizy.toolbox.useragent;

/**
 * @author Nidhal Dogga
 * @created 9/11/2022 6:15 PM
 */

public record UserAgent(
  String appName,
  String appVersion,
  String platform,
  String platformVersion,
  String deviceName,
  String deviceModel,
  String deviceArch
) {
}
