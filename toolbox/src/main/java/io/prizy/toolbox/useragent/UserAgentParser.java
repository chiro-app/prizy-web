package io.prizy.toolbox.useragent;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 9/11/2022 6:00 PM
 */


@UtilityClass
public class UserAgentParser {

  private final Pattern USER_AGENT_PATTERN = Pattern
    .compile("^(?<appname>.+)/(?<appversion>\\d\\.\\d\\.\\d) \\((?<info>.*)\\)$");

  private final String APP_NAME_GROUP = "appname";
  private final String APP_VERSION_GROUP = "appversion";
  private final String INFO_GROUP = "info";

  public Optional<UserAgent> parse(String userAgent) {
    var matcher = USER_AGENT_PATTERN.matcher(userAgent);
    if (!matcher.matches()) {
      return Optional.empty();
    }
    var info = Arrays.stream(matcher.group(INFO_GROUP).split(";"))
      .map(String::trim)
      .toList();
    var deviceInfo = info.get(0).split(" ");
    return Optional.of(new UserAgent(
      matcher.group(APP_NAME_GROUP),
      matcher.group(APP_VERSION_GROUP),
      deviceInfo[0],
      deviceInfo[1],
      info.get(1),
      info.get(2),
      info.get(3)
    ));
  }

}
