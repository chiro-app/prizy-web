package io.prizy.test.assertion;

import java.util.Collection;

import com.ekino.oss.jcv.assertion.hamcrest.JsonMatchers;
import io.prizy.test.extension.MockServerExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockserver.model.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal Dogga
 * @created 17/08/2022 11:09
 */

public interface MockServerAssertions {

  @RegisterExtension
  MockServerExtension MOCK_SERVER_EXTENSION = new MockServerExtension();


  default MockServerRequestAssertion assertThatMockServer(String path) {
    return new MockServerRequestAssertion(MOCK_SERVER_EXTENSION.retrieveRequests(path));
  }

  @RequiredArgsConstructor
  class MockServerRequestAssertion {

    protected final Collection<HttpRequest> recordedRequests;

    public MockServerRequestAssertion hasBody(String json) {
      assertThat(recordedRequests).anyMatch(request -> JsonMatchers.jsonMatcher(request.getBodyAsJsonOrXmlString()).matches(json));
      return this;
    }

  }

}
