package io.prizy.configuration;

import io.minio.MinioClient;
import io.prizy.adapters.asset.MinioClientProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackages = "io.prizy.adapters")
@ComponentScan(basePackages = "io.prizy.adapters")
@EnableJpaRepositories(basePackages = "io.prizy.adapters")
public class AdapterAutoConfiguration {

  @Bean
  public MinioClient minioClient(MinioClientProperties clientProperties) {
    return MinioClient.builder()
      .endpoint(clientProperties.endpoint())
      .credentials(clientProperties.accessKey(), clientProperties.secretKey())
      .region(clientProperties.region())
      .build();
  }

}