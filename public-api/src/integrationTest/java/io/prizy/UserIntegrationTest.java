package io.prizy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Nidhal Dogga
 * @created 23/08/2022 17:03
 */

public class UserIntegrationTest extends IntegrationTest {

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

    assertThatTable("referral_nodes").hasNumberOfRows(1);
  }

}
