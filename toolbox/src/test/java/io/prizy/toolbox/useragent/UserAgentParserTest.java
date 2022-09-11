package io.prizy.toolbox.useragent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Nidhal Dogga
 * @created 9/11/2022 6:51 PM
 */

class UserAgentParserTest {

  @Test
  @DisplayName("Should extract correct user agent against valid input")
  void shouldExtractCorrectUserAgent() {
    var agent = UserAgentParser.parse("Chiro Debug/1.5.1 (Android 12; sdk_gphone64_x86_64; emulator64_x86_64_arm64; x86_64)");
    assertThat(agent)
      .isNotEmpty()
      .hasValue(new UserAgent(
        "Chiro Debug",
        "1.5.1",
        "Android",
        "12",
        "sdk_gphone64_x86_64",
        "emulator64_x86_64_arm64",
        "x86_64"
      ));
    agent = UserAgentParser.parse("SampleApp/1.0.0 (Android 11; Pixel 4 XL; coral; arm64-v8a)");
    assertThat(agent)
      .isNotEmpty()
      .hasValue(new UserAgent(
        "SampleApp",
        "1.0.0",
        "Android",
        "11",
        "Pixel 4 XL",
        "coral",
        "arm64-v8a"
      ));
  }

  @Test
  @DisplayName("Should return null against invalid input")
  void shouldReturnEmptyAgainstInvalidInput() {
    var agent = UserAgentParser.parse("Chiro Debug/1.5.1");
    assertThat(agent).isEmpty();
  }

}