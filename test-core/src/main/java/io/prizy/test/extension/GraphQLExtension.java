package io.prizy.test.extension;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import io.prizy.auth.AuthProvider;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.Objects.requireNonNull;

/**
 * @author Nidhal Dogga
 * @created 8/15/2022 10:28 PM
 */


@Slf4j
public class GraphQLExtension implements BeforeAllCallback, AfterAllCallback {

  private static final String LOCAL_SERVER_PORT_PROPERTY_NAME = "local.server.port";

  private AuthProvider authProvider;

  @Override
  public void beforeAll(ExtensionContext context) {
    var springContext = SpringExtension.getApplicationContext(context);
    var env = springContext.getEnvironment();
    RestAssured.port = Integer.parseInt(requireNonNull(env.getProperty(LOCAL_SERVER_PORT_PROPERTY_NAME)));
    authProvider = springContext.getBean(AuthProvider.class);
  }

  @Override
  public void afterAll(ExtensionContext context) {
    RestAssured.reset();
  }

  public String getTokenForUser(UUID userId) {
    try {
      return authProvider.authenticate(userId).get().getToken();
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

}
