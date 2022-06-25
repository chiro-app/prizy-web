package io.prizy.publicapi.application.configuration;

import io.prizy.adapters.asset.MinioClientProperties;
import io.prizy.publicapi.application.properties.AppConfigurationProperties;
import io.prizy.publicapi.application.properties.GameDescriptionProperties;
import io.prizy.publicapi.application.properties.GameProperties;
import io.prizy.publicapi.application.properties.JwtProperties;
import io.prizy.publicapi.application.properties.ResourceProperties;
import io.prizy.publicapi.application.properties.ServerProperties;
import io.prizy.publicapi.application.properties.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 10:03 PM
 */


@Configuration
@EnableConfigurationProperties({
  JwtProperties.class,
  GameProperties.class,
  ServerProperties.class,
  StorageProperties.class,
  ResourceProperties.class,
  GameDescriptionProperties.class,
  GameDescriptionProperties.class,
  AppConfigurationProperties.class
})
public class PublicApiConfiguration {

  @Bean
  MinioClientProperties minioClientProperties(StorageProperties storageProperties) {
    return new MinioClientProperties(
      storageProperties.endpoint(),
      storageProperties.accessKey(),
      storageProperties.secretKey(),
      storageProperties.bucketName(),
      storageProperties.region()
    );
  }

  @Bean
  io.prizy.domain.resources.properties.ResourceProperties resourceProperties(ResourceProperties resourceProperties) {
    return resourceProperties.getDomain();
  }

  @Bean
  io.prizy.domain.game.properties.GameProperties gameProperties(GameProperties gameProperties) {
    return gameProperties.getDomain();
  }

}
