package io.prizy.test.assertion;

import io.prizy.test.extension.MockServerExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;

import static org.mockserver.model.HttpResponse.response;

/**
 * @author Nidhal Dogga
 * @created 17/08/2022 11:09
 */

public interface MockServerAssertions {

  @RegisterExtension
  MockServerExtension MOCK_SERVER_EXTENSION = new MockServerExtension();

  default void mockAnyRequest(HttpResponse response) {
    MOCK_SERVER_EXTENSION.mockAnyRequest(response);
  }

  default void mockRequest(HttpRequest request, HttpResponse response) {
    MOCK_SERVER_EXTENSION.mockRequest(request, response);
  }

  default void mockOneSignal() {
    mockAnyRequest(response()
      .withStatusCode(200)
      .withContentType(MediaType.APPLICATION_JSON)
      // Sample OK response
      .withBody("""
        {
          "id": "8f70ca11-1dca-4bfc-a20a-dde8fb031f64",
          "recipients": 1,
          "external_id": "ef2da183-ee68-4214-8728-1e1a8cc5a655"
        }
        """));
  }

}
