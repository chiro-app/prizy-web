package io.prizy.publicapi.port.dto;

import lombok.Builder;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:26 PM
 */


@Builder
public record AppConfigurationDto(
  String usageConditionsUrl,
  String minimumAppVersion,
  String latestAppVersion,
  String privacyPolicyUrl,
  String legalNoticeUrl
) {

}
