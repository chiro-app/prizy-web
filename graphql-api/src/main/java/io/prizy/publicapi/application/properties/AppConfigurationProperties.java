package io.prizy.publicapi.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 12:06 AM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.app.version")
public record AppConfigurationProperties(
  String usageConditionsUrl,
  String minimumAppVersion,
  String latestAppVersion,
  String privacyPolicyUrl,
  String legalNoticeUrl
) {

}
