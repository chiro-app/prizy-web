package io.prizy.adapters.asset;

/**
 * @author Nidhal Dogga
 * @created 03/05/2022 10:43
 */

public record MinioClientProperties(
  String endpoint,
  String accessKey,
  String secretKey,
  String bucketName,
  String region
) {
}
