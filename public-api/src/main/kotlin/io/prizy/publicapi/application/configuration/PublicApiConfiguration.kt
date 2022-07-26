package io.prizy.publicapi.application.configuration

import io.prizy.publicapi.application.properties.AppConfigurationProperties
import io.prizy.publicapi.application.properties.GameDescriptionProperties
import io.prizy.publicapi.application.properties.GameProperties
import io.prizy.publicapi.application.properties.PushNotificationProperties
import io.prizy.publicapi.application.properties.ResourceProperties
import io.prizy.publicapi.application.properties.ServerProperties
import io.prizy.publicapi.application.properties.StorageProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

/**
 *  @author Nidhal Dogga
 *  @created 5/7/2022 11:18 AM
 */

@EnableAsync
@Configuration
@ComponentScan(basePackages = ["io.prizy.publicapi"])
@EnableConfigurationProperties(
  GameProperties::class,
  ServerProperties::class,
  StorageProperties::class,
  ResourceProperties::class,
  GameDescriptionProperties::class,
  AppConfigurationProperties::class,
  PushNotificationProperties::class,
)
class PublicApiConfiguration {

  @Bean
  fun minioClientProperties(storageProperties: StorageProperties) = storageProperties.toDomain

  @Bean
  fun resourceProperties(resourceProperties: ResourceProperties) = resourceProperties.toDomain

  @Bean
  fun gameProperties(gameProperties: GameProperties) = gameProperties.toDomain

  @Bean
  fun pushProperties(pushProperties: PushNotificationProperties) = pushProperties.toDomain
}