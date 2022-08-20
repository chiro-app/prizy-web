package io.prizy.test.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.prizy.test.annotation.WithMockServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
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

public class MockServerExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {

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
  public void afterEach(ExtensionContext context) {
    expectations.forEach(expectation -> mockserver.clear(expectation.getId()));
  }

  public void mockAnyRequest(HttpResponse response) {
    expectations.addAll(List.of(mockserver.when(request()).respond(response)));
  }

  public void mockRequest(HttpRequest request, HttpResponse response) {
    expectations.addAll(List.of(mockserver.when(request).respond(response)));
  }

}
