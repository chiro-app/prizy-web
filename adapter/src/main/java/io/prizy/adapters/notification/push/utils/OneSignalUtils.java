package io.prizy.adapters.notification.push.utils;

import java.util.Map;

import io.prizy.adapters.notification.push.model.OneSignalLocale;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 26/07/2022 11:49
 */


@UtilityClass
public class OneSignalUtils {

  public Map<OneSignalLocale, String> simpleLocalizedString(String text) {
    return Map.of(
      OneSignalLocale.ENGLISH, text,
      OneSignalLocale.FRENCH, text
    );
  }

}
