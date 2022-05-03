package io.prizy.adapters.asset;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.prizy.domain.asset.port.StorageBackend;
import io.prizy.toolbox.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 8:04 AM
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class MinioStorageBackend implements StorageBackend {

  private static final Integer PART_SIZE = 10485760;

  private final MinioClient client;
  private final MinioClientProperties properties;

  @Override
  public void putObject(InputStream inputStream, String path) {
    var request = PutObjectArgs.builder()
      .bucket(properties.bucketName())
      .object(path)
      .stream(inputStream, -1, PART_SIZE)
      .build();
    try {
      client.putObject(request);
    } catch (NoSuchAlgorithmException | InvalidKeyException | MinioException | IOException e) {
      log.error("Error while uploading file", e);
      throw new InternalServerException();
    }
  }

  @Override
  public String downloadUrl(String path) {
    var request = GetPresignedObjectUrlArgs.builder()
      .object(path)
      .method(Method.GET)
      .bucket(properties.bucketName())
      .expiry(1, TimeUnit.HOURS)
      .build();
    try {
      return client.getPresignedObjectUrl(request);
    } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
      log.error("Error while generating download url", e);
      throw new InternalServerException();
    }
  }

}
