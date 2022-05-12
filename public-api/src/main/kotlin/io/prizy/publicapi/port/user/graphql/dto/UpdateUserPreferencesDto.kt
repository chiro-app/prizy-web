package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 10:29 PM
 */

@GraphQLName("UpdateUserPreferences")
data class UpdateUserPreferencesDto(
  val notifications: NotificationSettingsDto,
  val emails: EmailSettingsDto
)