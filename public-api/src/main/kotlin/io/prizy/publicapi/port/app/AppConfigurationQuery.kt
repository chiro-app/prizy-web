package io.prizy.publicapi.port.app

import com.expediagroup.graphql.server.operations.Query
import io.prizy.publicapi.application.properties.AppConfigurationProperties
import io.prizy.publicapi.port.app.dto.AppConfigurationDto
import io.prizy.publicapi.port.app.mapper.AppConfigurationDtoMapper
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 12:13 AM
 */

@Component
class AppConfigurationQuery(private val appVersionProperties: AppConfigurationProperties) : Query {

  fun appConfiguration(): AppConfigurationDto = AppConfigurationDtoMapper.map(appVersionProperties)
}