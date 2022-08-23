package io.prizy;

import io.prizy.publicapi.application.Application;
import io.prizy.test.annotation.WithMockServer;
import io.prizy.test.assertion.DatabaseAssertions;
import io.prizy.test.assertion.GraphQLAssertions;
import io.prizy.test.assertion.MockServerAssertions;
import io.prizy.test.util.ResourceUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationEventPublisher;
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
public abstract class IntegrationTest implements GraphQLAssertions, DatabaseAssertions, MockServerAssertions {

  @Autowired
  private ApplicationEventPublisher aep;

  protected void publishEvent(Object event) {
    aep.publishEvent(event);
  }

  protected String resourceFile(String name) {
    return ResourceUtils.resourceFile(getClass(), name);
  }

}
