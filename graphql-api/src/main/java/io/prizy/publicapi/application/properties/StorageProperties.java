package io.prizy.publicapi.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 12:21 AM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.storage")
public record StorageProperties(
  String endpoint,
  String bucketName,
  String accessKey,
  String secretKey,
  String serverUrl,
  String region
) {

}