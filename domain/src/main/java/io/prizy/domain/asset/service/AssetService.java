package io.prizy.domain.asset.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.asset.model.Asset;
import io.prizy.domain.asset.port.AssetRepository;
import io.prizy.domain.asset.port.StorageBackend;
import io.prizy.toolbox.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:46 AM
 */


@Slf4j
@Service
@Transactional
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
      var fs = new FileInputStream(temporaryFile);
      var asset = upload(
        fs,
        fileId,
        filePart.filename(),
        temporaryFile.length()
      );

      fs.close();
      Files.deleteIfExists(temporaryPath);

      return asset;
    } catch (IOException e) {
      log.error("Error while uploading file", e);
      throw new InternalServerException();
    }
  }

  public Optional<Asset> get(String id) {
    return assetRepository.byId(id);
  }

  public Boolean exists(String id) {
    return assetRepository.existsById(id);
  }

  public Collection<Asset> getMany(Collection<String> ids) {
    return assetRepository.byIds(ids);
  }

  public Optional<String> getDownloadUrl(String assetId) {
    return assetRepository
      .byId(assetId)
      .map(asset -> storageBackend.downloadUrl(asset.path()));
  }

}
