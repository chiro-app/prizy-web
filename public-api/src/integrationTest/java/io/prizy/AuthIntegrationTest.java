package io.prizy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:56 PM
 */


@DisplayName("Auth")
public class AuthIntegrationTest extends IntegrationTest {

  @Test
  @Sql("authintegrationtest/sql/users.sql")
  @DisplayName("Should return jwt against valid credentials")
  void shouldReturnJwtAgainstValidCredentials() {
    whenMutating("login")
      .then()
      .assertThat()
      .body(jsonMatcher("json/jwt-response.json"));

    assertThatTable("refresh_tokens", "user_id")
      .hasNumberOfRows(2)
      .row(0)
      .value("user_id").isEqualTo("00000000-0000-0000-0000-000000000000");
  }

  @Test
  @DisplayName("Should return error if user not found")
  void shouldReturnNotFoundWhenUserNotFound() {
    whenMutating("login")
      .then()
      .assertThat()
      .body(jsonMatcher("json/invalid-credentials.json"));
  }

  @Test
  @Sql("authintegrationtest/sql/users.sql")
  @DisplayName("Should return error against invalid credentials")
  void shouldReturnErrorAgainstInvalidCredentials() {
    whenMutating("login_invalid_password")
      .then()
      .assertThat()
      .body(jsonMatcher("json/invalid-credentials.json"));
  }

  @Test
  @Sql("authintegrationtest/sql/users.sql")
  @DisplayName("Should return existing refresh token when found")
  void shouldReturnExistingRefreshTokenWhenFound() {
    whenMutating("login_existing_refresh_token")
      .then()
      .assertThat()
      .body(jsonMatcher("json/jwt-response-existing-refresh-token.json"));

    assertThatTable("refresh_tokens").hasNumberOfRows(1);
  }

}
