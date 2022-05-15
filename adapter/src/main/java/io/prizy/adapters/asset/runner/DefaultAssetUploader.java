package io.prizy.adapters.asset.runner;

import java.io.IOException;
import java.util.Arrays;

import io.prizy.domain.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

/**
 * @author Nidhal Dogga
 * @created 5/15/2022 1:28 PM
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAssetUploader implements CommandLineRunner {

  private static final String ASSET_PATTERN = "classpath*:default-assets/*";

  private final AssetService assetService;
  private final ResourceLoader resourceLoader;

  @Override
  public void run(String... args) throws Exception {
    Arrays
      .stream(ResourcePatternUtils
        .getResourcePatternResolver(resourceLoader)
        .getResources(ASSET_PATTERN)
      )
      .forEach(resource -> {
        var assetId = FileNameUtils.getBaseName(resource.getFilename());
        if (!assetService.exists(assetId)) {
          try {
            var asset = assetService.upload(
              resource.getInputStream(),
              assetId,
              resource.getFilename(),
              resource.contentLength()
            );
            log.info("Asset {} uploaded successfully", asset);
          } catch (IOException e) {
            log.error("Error while uploading asset {}", assetId, e);
          }
        } else {
          log.info("Asset {} already exists, skipping", assetId);
        }
      });
  }
}
