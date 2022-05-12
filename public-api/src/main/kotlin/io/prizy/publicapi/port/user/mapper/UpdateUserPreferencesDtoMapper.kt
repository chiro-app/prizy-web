package io.prizy.publicapi.port.user.mapper

import io.prizy.domain.user.model.EmailSettings
import io.prizy.domain.user.model.NotificationSettings
import io.prizy.domain.user.model.UserPreferences
import io.prizy.publicapi.port.user.graphql.dto.UpdateUserPreferencesDto
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 10:31 PM
 */

object UpdateUserPreferencesDtoMapper {

  fun map(dto: UpdateUserPreferencesDto, userId: UUID): UserPreferences = UserPreferences(
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