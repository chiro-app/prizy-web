package io.prizy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 1:32 PM
 */


@DisplayName("Application configuration")
public class AppConfigurationIntegrationTest extends IntegrationTest {

  @Test
  @DisplayName("Should return correct application configuration")
  void shouldReturnAppConfiguration() {
    whenQuerying("app_configuration")
      .then()
      .assertThat()
      .body(jsonMatcher("json/app-configuration.json"));
  }

}