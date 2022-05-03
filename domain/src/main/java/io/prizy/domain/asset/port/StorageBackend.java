package io.prizy.domain.asset.port;

import java.io.InputStream;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 7:50 AM
 */


public interface StorageBackend {
  void putObject(InputStream inputStream, String path);

  String downloadUrl(String path);
}
