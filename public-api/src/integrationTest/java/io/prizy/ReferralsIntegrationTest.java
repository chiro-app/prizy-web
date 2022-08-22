package io.prizy;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author Nidhal Dogga
 * @created 8/19/2022 8:46 PM
 */

@DisplayName("Referrals")
public class ReferralsIntegrationTest extends IntegrationTest {

  private static final UUID USER_ID_00 = UUID.fromString("00000000-0000-0000-0000-000000000000");
  private static final UUID USER_ID_01 = UUID.fromString("00000000-0000-0000-0000-000000000001");

  @Test
  @Sql("referralsintegrationtest/sql/users.sql")
  @DisplayName("Should apply correct referral code")
  void shouldApplyReferralCode() {
    stubOneSignal();

    whenMutating("apply_correct_referral_code", USER_ID_00)
      .then()
      .body(jsonMatcher("json/success-response.json"));

    assertThatTable("referral_nodes", "user_id")
      .row(0)
      .value("referrer_id").isEqualTo(USER_ID_01);

    assertThatTable("resource_transactions", "user_id")
      .hasNumberOfRows(2)
      .row(0)
      .value("user_id").isEqualTo(USER_ID_00)
      .value("currency").isEqualTo("KEYS")
      .value("amount").isEqualTo(6)
      .row(1)
      .value("user_id").isEqualTo(USER_ID_01)
      .value("currency").isEqualTo("KEYS")
      .value("amount").isEqualTo(6);

    assertRequestedWithBody("", resourceFile("onesignal/referral-push-notification.json"));
  }

  @Test
  @Sql("referralsintegrationtest/sql/users.sql")
  @DisplayName("Should return false against incorrect referral code")
  void shouldIgnoreIncorrectReferralCode() {
    whenMutating("apply_incorrect_referral_code", USER_ID_00)
      .then()
      .body(jsonMatcher("json/failure-response.json"));

    assertThatTable("referral_nodes", "user_id")
      .row(0)
      .value("referrer_id").isNull()
      .row(1)
      .value("referrer_id").isNull();

    assertThatTable("resource_transactions").hasNumberOfRows(0);
  }

}