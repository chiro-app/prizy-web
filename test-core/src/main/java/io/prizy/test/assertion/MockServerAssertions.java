package io.prizy.test.assertion;

import com.ekino.oss.jcv.assertion.hamcrest.JsonMatchers;
import io.prizy.test.extension.MockServerExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author Nidhal Dogga
 * @created 17/08/2022 11:09
 */

public interface MockServerAssertions {

  @RegisterExtension
  MockServerExtension MOCK_SERVER_EXTENSION = new MockServerExtension();

  default void stubAllRequests(String path, HttpResponse response) {
    MOCK_SERVER_EXTENSION.stubAllRequests(path, response);
  }

  default void stubRequest(HttpRequest request, HttpResponse response) {
    MOCK_SERVER_EXTENSION.stubRequest(request, response);
  }

  default void stubOneSignal() {
    stubAllRequests("/onesignal", response()
      .withStatusCode(200)
      .withContentType(MediaType.APPLICATION_JSON)
      // Sample OK response
      .withBody("""
        {
          "id": "00000000-0000-0000-0000-000000000000",
          "recipients": 1,
          "external_id": "00000000-0000-0000-0000-000000000000"
        }
        """)
    );
  }

  default void assertRequestedWithBody(String path, String jsonBody) {
    var requests = MOCK_SERVER_EXTENSION.retrieveRequests(path);
    assertThat(requests).anyMatch(request -> JsonMatchers.jsonMatcher(request.getBodyAsJsonOrXmlString()).matches(jsonBody));
  }

}
