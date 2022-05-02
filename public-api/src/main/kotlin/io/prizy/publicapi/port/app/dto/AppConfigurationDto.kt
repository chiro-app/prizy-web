package io.prizy.publicapi.port.app.dto

import com.expediagroup.graphql.generator.annotations.GraphQLName

/**
 *  @author Nidhal Dogga
 *  @created 5/3/2022 12:15 AM
 */

@GraphQLName("AppConfiguration")
data class AppConfigurationDto(
  val usageConditionsUrl: String,
  val minimumAppVersion: String,
  val latestAppVersion: String,
  val privacyPolicyUrl: String,
  val legalNoticeUrl: String
)
