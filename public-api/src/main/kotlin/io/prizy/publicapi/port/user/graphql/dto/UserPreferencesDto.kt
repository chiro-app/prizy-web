package io.prizy.publicapi.port.user.graphql.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 10:29 PM
 */

@GraphQLName("UserPreferences")
data class UserPreferencesDto(
  val notifications: NotificationSettingsDto,
  val emails: EmailSettingsDto
)

@GraphQLName("NotificationSettings")
data class NotificationSettingsDto(
  val leagueDowngrade: Boolean,
  val packWinning: Boolean,
  val newContests: Boolean,
  val couponExpiration: Boolean,
  val collectTickets: Boolean,
  val collectDiamonds: Boolean
)

@GraphQLName("EmailSettings")
data class EmailSettingsDto(
  val newsletter: Boolean,
  val tips: Boolean
)