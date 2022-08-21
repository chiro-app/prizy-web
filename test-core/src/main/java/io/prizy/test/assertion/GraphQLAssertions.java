package io.prizy.test.assertion;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.ekino.oss.jcv.assertion.hamcrest.JsonMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.prizy.test.extension.GraphQLExtension;
import io.prizy.test.util.ResourceUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.restassured.RestAssured.given;

/**
 * @author Nidhal Dogga
 * @created 8/14/2022 9:35 PM
 */


public interface GraphQLAssertions {

  @RegisterExtension
  GraphQLExtension GRAPHQL_EXTENSION = new GraphQLExtension();

  String QUERY_REQUEST_TYPE = "query";
  String MUTATION_REQUEST_TYPE = "mutation";
  String GRAPHQL_PATH = "/graphql";
  ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  default Response whenMutating(String mutationName) {
    return givenGraphQLRequest()
      .body(graphQLMutation(mutationName))
      .when()
      .post();
  }

  default Response whenMutating(String mutationName, UUID userId) {
    return givenAuthenticatedGraphQLRequest(userId)
      .body(graphQLMutation(mutationName))
      .when()
      .post();
  }

  default Response whenQuerying(String queryName) {
    return givenGraphQLRequest()
      .body(graphQLQuery(queryName))
      .when()
      .post();
  }

  default Response whenQuerying(String queryName, UUID userId) {
    return givenAuthenticatedGraphQLRequest(userId)
      .body(graphQLQuery(queryName))
      .when()
      .post();

  }

  default RequestSpecification givenGraphQLRequest() {
    return given()
      .basePath(GRAPHQL_PATH)
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON);
  }

  default RequestSpecification givenAuthenticatedGraphQLRequest(UUID userId) {
    return givenGraphQLRequest()
      .auth()
      .oauth2(GRAPHQL_EXTENSION.getTokenForUser(userId));
  }

  default String graphQLRequest(String extensionlessFileName, String requestType) {
    try {
      var operation = Map.of(
        "query", ResourceUtils.resourceFile(getClass(), String.format("graphql/%s/%s.graphql", requestType,
          extensionlessFileName))
      );
      return OBJECT_MAPPER.writeValueAsString(operation);
    } catch (IOException exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  default String graphQLQuery(String queryName) {
    return graphQLRequest(queryName, QUERY_REQUEST_TYPE);
  }

  default String graphQLMutation(String mutationName) {
    return graphQLRequest(mutationName, MUTATION_REQUEST_TYPE);
  }

  default Matcher<String> jsonMatcher(String fileName) {
    return JsonMatchers.jsonMatcher(ResourceUtils.resourceFile(getClass(), fileName));
  }

}
