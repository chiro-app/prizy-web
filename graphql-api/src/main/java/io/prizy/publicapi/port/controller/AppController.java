package io.prizy.publicapi.port.controller;

import io.prizy.publicapi.application.properties.AppConfigurationProperties;
import io.prizy.publicapi.port.dto.AppConfigurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:24 PM
 */


@Controller
@RequiredArgsConstructor
public class AppController {

  private final AppConfigurationProperties properties;

  @QueryMapping
  public AppConfigurationDto appConfiguration() {
    return AppConfigurationDto.builder()
      .latestAppVersion(properties.latestAppVersion())
      .minimumAppVersion(properties.minimumAppVersion())
      .legalNoticeUrl(properties.legalNoticeUrl())
      .privacyPolicyUrl(properties.privacyPolicyUrl())
      .usageConditionsUrl(properties.usageConditionsUrl())
      .build();
  }

}
