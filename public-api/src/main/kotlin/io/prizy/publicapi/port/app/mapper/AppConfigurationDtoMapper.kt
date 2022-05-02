package io.prizy.publicapi.port.app.mapper

import io.prizy.publicapi.application.properties.AppConfigurationProperties
import io.prizy.publicapi.port.app.dto.AppConfigurationDto

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 12:15 AM
 */

object AppConfigurationDtoMapper {

  fun map(properties: AppConfigurationProperties): AppConfigurationDto = AppConfigurationDto(
    legalNoticeUrl = properties.legalNoticeUrl,
    privacyPolicyUrl = properties.privacyPolicyUrl,
    usageConditionsUrl = properties.usageConditionsUrl,
    minimumAppVersion = properties.minimumAppVersion,
    latestAppVersion = properties.latestAppVersion
  )
}