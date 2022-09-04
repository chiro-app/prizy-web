package io.prizy;

import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 8/26/2022 12:55 AM
 */


@DisplayName("Contests")
public class ContestsIntegrationTest extends IntegrationTest {

  private static final UUID ADMIN_USER = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID GUEST_USER = UUID.fromString("00000000-0000-0000-0000-000000000001");

  private static final TimeZone ACTUAL_TIME_ZONE = TimeZone.getDefault();

  @BeforeAll
  static void setUp() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @AfterAll
  static void tearDown() {
    TimeZone.setDefault(ACTUAL_TIME_ZONE);
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @Sql("contestsintegrationtest/sql/assets.sql")
  @Sql("contestsintegrationtest/sql/boards.sql")
  @DisplayName("Should create contest against correct input")
  void shouldCreateContestAgainstCorrectInput() {
    whenMutating("create_contest", ADMIN_USER)
      .then()
      .body(jsonMatcher("json/created-contest.json"));

    assertThatTable("contests")
      .hasNumberOfRows(1)
      .row()
      .value("name").isEqualTo("Test Contest")
      .value("from_date").isEqualTo(dateTimeValue("2022-01-01 00:00:00"))
      .value("to_date").isEqualTo(dateTimeValue("2023-01-01 00:00:00"))
      .value("category").isEqualTo("FASHION");

    assertThatTable("packs", "dtype")
      .hasNumberOfRows(2)
      .row(0)
        .value("dtype").isEqualTo("Coupon")
        .value("name").isEqualTo("Coupon")
        .value("code").isEqualTo("AABBC")
        .value("value").isNull()
        .value("asset_id").isNull()
        .value("expiration_date").isEqualTo(dateTimeValue("2024-01-01 00:00:00"))
      .row(1)
        .value("dtype").isEqualTo("Product")
        .value("name").isEqualTo("Product")
        .value("value").isEqualTo(123.99)
        .value("quantity").isEqualTo(1)
        .value("code").isNull()
        .value("expiration_date").isNull();
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @Sql("contestsintegrationtest/sql/assets.sql")
  @Sql("contestsintegrationtest/sql/boards.sql")
  @Sql("contestsintegrationtest/sql/contests.sql")
  @DisplayName("Should update contest against correct input")
  void shouldUpdateContestAgainstCorrectInput() {
    whenMutating("update_contest", ADMIN_USER)
      .then()
      .body(jsonMatcher("json/updated-contest.json"));

    assertThatTable("contests", "id")
      .hasNumberOfRows(2)
      .row()
      .value("name").isEqualTo("Test Contest")
      .value("from_date").isEqualTo(dateTimeValue("2022-01-01 00:00:00"))
      .value("to_date").isEqualTo(dateTimeValue("2023-01-01 00:00:00"))
      .value("category").isEqualTo("FASHION");

    assertThatTable("packs", "dtype")
      .hasNumberOfRows(2)
      .row(0)
        .value("dtype").isEqualTo("Coupon")
        .value("name").isEqualTo("Coupon")
        .value("code").isEqualTo("AABBC")
        .value("value").isNull()
        .value("asset_id").isNull()
        .value("expiration_date").isEqualTo(dateTimeValue("2024-01-01 00:00:00"))
      .row(1)
        .value("dtype").isEqualTo("Product")
        .value("name").isEqualTo("Product")
        .value("value").isEqualTo(123.99)
        .value("quantity").isEqualTo(1)
        .value("code").isNull()
        .value("expiration_date").isNull();
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @Sql("contestsintegrationtest/sql/assets.sql")
  @Sql("contestsintegrationtest/sql/boards.sql")
  @Sql("contestsintegrationtest/sql/contests.sql")
  @DisplayName("Should return CONTEST_ALREADY_ENDED for update ended contest")
  void shouldReturnContestAlreadyEndedWhenUpdating() {
    whenMutating("update_ended_contest", ADMIN_USER)
      .then()
      .body(jsonMatcher("json/contest-already-ended.json"));

    assertThatTable("contests")
      .hasNumberOfRows(2)
      .row(0)
      .value("name").isEqualTo("PREM");
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @Sql("contestsintegrationtest/sql/assets.sql")
  @Sql("contestsintegrationtest/sql/boards.sql")
  @DisplayName("Should return CONTEST_NOT_FOUND for update contest with id not found")
  void shouldReturnContestNotFoundForInvalidIds() {
    whenMutating("update_contest", ADMIN_USER)
      .then()
      .body(jsonMatcher("json/contest-not-found.json"));

    assertThatTable("contests").isEmpty();
    assertThatTable("packs").isEmpty();
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @DisplayName("Should return INSUFFICIENT_CREDENTIALS for guest users")
  void shouldReturnInsufficientCredentialsForGuestUsers() {
    whenMutating("create_contest", GUEST_USER)
      .then()
      .body(jsonMatcher("json/insufficient-credentials.json"));

    assertThatTable("contests").isEmpty();
    assertThatTable("packs").isEmpty();
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @DisplayName("Should return INSUFFICIENT_CREDENTIALS for guest users for create contest")
  void shouldReturnInsufficientCredentialsForGuestUsersWhenCreate() {
    whenMutating("create_contest", GUEST_USER)
      .then()
      .body(jsonMatcher("json/insufficient-credentials.json"));

    assertThatTable("contests").isEmpty();
    assertThatTable("packs").isEmpty();
  }

  @Test
  @Sql("contestsintegrationtest/sql/users.sql")
  @DisplayName("Should return INSUFFICIENT_CREDENTIALS for guest users for update contest")
  void shouldReturnInsufficientCredentialsForGuestUsersWhenUpdate() {
    whenMutating("update_contest", GUEST_USER)
      .then()
      .body(jsonMatcher("json/insufficient-credentials.json"));

    assertThatTable("contests").isEmpty();
    assertThatTable("packs").isEmpty();
  }

}
