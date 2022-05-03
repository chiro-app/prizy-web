package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.EmailSettings
import io.prizy.domain.user.model.NotificationSettings
import io.prizy.domain.user.model.UserPreferences
import io.prizy.publicapi.port.user.graphql.dto.EmailSettingsDto
import io.prizy.publicapi.port.user.graphql.dto.NotificationSettingsDto
import io.prizy.publicapi.port.user.graphql.dto.UserPreferencesDto
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 10:31 PM
 */

object UserPreferencesDtoMapper {

  fun map(userPreferences: UserPreferences): UserPreferencesDto = UserPreferencesDto(
    emails = EmailSettingsDto(
      newsletter = userPreferences.emails.newsletter,
      tips = userPreferences.emails.tips
    ),
    notifications = NotificationSettingsDto(
      leagueDowngrade = userPreferences.notifications.leagueDowngrade,
      packWinning = userPreferences.notifications.packWinning,
      newContests = userPreferences.notifications.newContests,
      couponExpiration = userPreferences.notifications.couponExpiration,
      collectTickets = userPreferences.notifications.collectTickets,
      collectDiamonds = userPreferences.notifications.collectDiamonds
    )
  )

  fun map(dto: UserPreferencesDto, userId: UUID): UserPreferences = UserPreferences(
    userId,
    NotificationSettings(
      dto.notifications.leagueDowngrade,
      dto.notifications.packWinning,
      dto.notifications.newContests,
      dto.notifications.couponExpiration,
      dto.notifications.collectTickets,
      dto.notifications.collectDiamonds
    ),
    EmailSettings(
      dto.emails.newsletter,
      dto.emails.tips
    )
  )
}