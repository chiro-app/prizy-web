package io.prizy.domain.asset.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.asset.model.Asset;
import io.prizy.domain.asset.port.AssetRepository;
import io.prizy.domain.asset.port.StorageBackend;
import io.prizy.toolbox.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.lang.Nullable;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:46 AM
 */


@Slf4j
@RequiredArgsConstructor
public class AssetService {

  private final StorageBackend storageBackend;
  private final AssetRepository assetRepository;

  public Asset upload(InputStream inputStream, String id, String name, Long size) {
    var path = Instant.now().getEpochSecond() + "-" + name.replace(" ", "_");

    storageBackend.putObject(inputStream, path);

    try {
      return assetRepository.save(Asset.builder()
        .id(id)
        .originalName(name)
        .size(size)
        .path(path)
        .type(Files.probeContentType(Paths.get(name)))
        .build()
      );
    } catch (IOException e) {
      log.error("Error while uploading file", e);
      throw new InternalServerException();
    }
  }

  public Asset upload(FilePart filePart) {
    try {
      var temporaryPath = Files.createTempFile(UUID.randomUUID().toString(), filePart.filename());
      var temporaryFile = temporaryPath.toFile();

      filePart.transferTo(temporaryFile).block();

      var fileId = UUID.randomUUID().toString();
      var asset = upload(
        new FileInputStream(temporaryFile),
        fileId,
        filePart.filename(),
        temporaryFile.length()
      );

      Files.deleteIfExists(temporaryPath);

      return asset;
    } catch (IOException e) {
      log.error("Error while uploading file", e);
      throw new InternalServerException();
    }
  }

  public Asset upload(Resource resource) throws IOException {
    return upload(resource, null);
  }

  public Asset upload(Resource resource, @Nullable String fileName) {
    var resourceFileName = Optional.ofNullable(resource.getFilename());
    try {
      return upload(
        resource.getInputStream(),
        resourceFileName.map(FilenameUtils::removeExtension).orElseGet(UUID.randomUUID()::toString),
        resourceFileName.orElseGet(UUID.randomUUID()::toString),
        resource.contentLength()
      );
    } catch (IOException e) {
      log.error("Error while uploading resource", e);
      throw new InternalServerException();
    }
  }

  public Optional<Asset> get(String id) {
    return assetRepository.byId(id);
  }

  public Optional<String> getDownloadUrl(String assetId) {
    return assetRepository
      .byId(assetId)
      .map(asset -> storageBackend.downloadUrl(asset.path()));
  }

}
