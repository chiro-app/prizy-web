package io.prizy.test.util;

import java.io.IOException;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Nidhal Dogga
 * @created 21/08/2022 17:51
 */


@UtilityClass
public class ResourceUtils {

  public String resourceFile(Class<?> cls, String path) {
    try {
      var resource = new ClassPathResource(String.format("%s/%s", baseResourcePath(cls), path));
      return new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  String baseResourcePath(Class<?> cls) {
    return String
      .format("%s.%s", cls.getPackageName(), cls.getSimpleName().toLowerCase())
      .replaceAll("\\.", "/");
  }

}
