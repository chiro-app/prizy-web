package io.prizy;

import java.sql.Timestamp;

import io.prizy.publicapi.application.Application;
import io.prizy.test.annotation.WithMockServer;
import io.prizy.test.assertion.DatabaseAssertions;
import io.prizy.test.assertion.GraphQLAssertions;
import io.prizy.test.assertion.MailAssertions;
import io.prizy.test.assertion.MockServerAssertions;
import io.prizy.test.assertion.PushNotificationAssertions;
import io.prizy.test.assertion.ScheduledTasksAssertions;
import org.assertj.db.type.DateTimeValue;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 1:09 PM
 */


@WithMockServer
@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest implements GraphQLAssertions, DatabaseAssertions, MockServerAssertions,
  MailAssertions, PushNotificationAssertions, ScheduledTasksAssertions {

  protected DateTimeValue dateTimeValue(String raw) {
    return DateTimeValue.from(Timestamp.valueOf(raw));
  }

}
