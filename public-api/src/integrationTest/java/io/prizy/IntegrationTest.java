package io.prizy;

import io.prizy.publicapi.application.Application;
import io.prizy.test.annotation.WithMockServer;
import io.prizy.test.assertion.DatabaseAssertions;
import io.prizy.test.assertion.GraphQLAssertions;
import io.prizy.test.assertion.MockServerAssertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 1:09 PM
 */


@WithMockServer
@ActiveProfiles("test")
@RecordApplicationEvents
@AutoConfigureTestDatabase
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest implements GraphQLAssertions, DatabaseAssertions, MockServerAssertions {

  @Autowired
  private ApplicationEvents applicationEvents;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  protected void sendEvent(Object event) {
    applicationEventPublisher.publishEvent(event);
  }

  protected <T> void assertThatEventCount(Class<T> cls, Integer count) {
    assertThat(count).isEqualTo(applicationEvents.stream(cls).count());
  }

  protected <T> void assertThatEventEquals(Class<T> cls, Integer index, Object expected) {
    assertThat(expected).isEqualTo(applicationEvents.stream(cls).toList().get(index));
  }

}
