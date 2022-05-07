package io.prizy.publicapi.application.configuration

import io.prizy.adapters.asset.MinioClientProperties
import io.prizy.publicapi.application.properties.ResourceProperties
import io.prizy.publicapi.application.properties.StorageProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  @author Nidhal Dogga
 *  @created 5/7/2022 11:18 AM
 */

@Configuration
class PublicApiConfiguration {

  @Bean
  fun minioClientProperties(storageProperties: StorageProperties) = MinioClientProperties(
    storageProperties.endpoint,
    storageProperties.accessKey,
    storageProperties.secretKey,
    storageProperties.bucketName,
    storageProperties.region
  )

  @Bean
  fun resourceProperties(resourceProperties: ResourceProperties) = resourceProperties.toDomain
}