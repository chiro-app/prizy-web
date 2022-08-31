package io.prizy.test.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.prizy.test.annotation.WithMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import static org.mockserver.model.HttpRequest.request;

/**
 * @author Nidhal Dogga
 * @created 17/08/2022 11:07
 */


@Slf4j
public class MockServerExtension implements BeforeAllCallback, AfterAllCallback, AfterTestExecutionCallback {

  private final Collection<Expectation> expectations = new ArrayList<>();
  private ClientAndServer mockserver;

  @Override
  public void beforeAll(ExtensionContext context) {
    var clazz = context.getElement().get().getClass();
    var port = clazz.isAnnotationPresent(WithMockServer.class)
      ? clazz.getAnnotation(WithMockServer.class).port()
      : WithMockServer.DEFAULT_PORT;
    mockserver = ClientAndServer.startClientAndServer(port);
  }

  @Override
  public void afterAll(ExtensionContext context) {
    mockserver.stop();
  }

  @Override
  public void afterTestExecution(ExtensionContext context) {
    expectations.stream().map(Expectation::getId).forEach(mockserver::clear);
    expectations.removeAll(expectations);
  }

  public void stubAllRequests(String path, HttpResponse response) {
    expectations.addAll(List.of(mockserver.when(request().withPath(path)).respond(response)));
  }

  public void stubRequest(HttpRequest request, HttpResponse response) {
    expectations.addAll(List.of(mockserver.when(request).respond(response)));
  }

  public Collection<HttpRequest> retrieveRequests(String path) {
    return List.of(mockserver.retrieveRecordedRequests(request(path)));
  }

  public Collection<HttpRequest> retrieveRequests() {
    return List.of(mockserver.retrieveRecordedRequests(request()));
  }

}
