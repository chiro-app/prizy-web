package io.prizy;

import java.util.UUID;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 23/08/2022 17:03
 */


@DisplayName("Users")
public class UsersIntegrationTest extends IntegrationTest {

  @Test
  @DisplayName("Should create user with valid info")
  void shouldCreateUserWithValidInput() {
    whenMutating("create_user")
      .then()
      .body(jsonMatcher("json/user-created-successfully.json"));

    assertThatTable("users")
      .hasNumberOfRows(1)
      .row(0)
      .value("email").isEqualTo("john.doe@email.com")
      .value("username").isEqualTo("jdoe")
      .value("first_name").isEqualTo("John")
      .value("last_name").isEqualTo("Doe")
      .value("gender").isEqualTo("MALE")
      .value("password_hash").isNotNull()
      .value("birth_date").isEqualTo("1990-10-10")
      .value("status").isEqualTo("PENDING");

    var userId = queryForObject("select id from users", UUID.class);

    assertThatTable("referral_nodes")
      .hasNumberOfRows(1)
      .row(0)
      .value("user_id").isEqualTo(userId);

    assertThatTable("confirmation_codes")
      .hasNumberOfRows(1)
      .row(0)
      .value("user_id").isEqualTo(userId);

    var confirmationCode = queryForObject("select code from confirmation_codes", String.class);
    var expectedSubject = String.format("%s - Bienvenue sur Chiro", confirmationCode);

    Awaitility
      .await()
      .untilAsserted(() -> assertThatEmailSent("john.doe@email.com", expectedSubject));
  }

  @Test
  @Sql("usersintegrationtest/sql/users.sql")
  @DisplayName("Should return USER_EXISTS when email is used")
  void shouldReturnErrorWhenEmailIsUsed() {
    whenMutating("create_user_existing_email")
      .then()
      .body(jsonMatcher("json/user-exists-error.json"));

    assertThatTable("users").hasNumberOfRows(1);
    assertThatTable("referral_nodes").isEmpty();
    assertThatTable("confirmation_codes").isEmpty();
  }

  @Test
  @Sql("usersintegrationtest/sql/users.sql")
  @DisplayName("Should return USER_EXISTS when username is used")
  void shouldReturnErrorWhenUsernameIsUsed() {
    whenMutating("create_user_existing_username")
      .then()
      .body(jsonMatcher("json/user-exists-error.json"));

    assertThatTable("users").hasNumberOfRows(1);
    assertThatTable("referral_nodes").isEmpty();
    assertThatTable("confirmation_codes").isEmpty();
  }

}
