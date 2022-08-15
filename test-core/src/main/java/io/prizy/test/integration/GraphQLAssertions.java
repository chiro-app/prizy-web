package io.prizy.test.integration;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.prizy.test.extension.GraphQLExtension;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONException;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.core.io.ClassPathResource;

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

  default RequestSpecification givenGraphQL() {
    return given()
      .basePath(GRAPHQL_PATH)
      .contentType(ContentType.JSON)
      .accept(ContentType.JSON);
  }

  default String graphQLRequest(String extensionlessFileName, String requestType) {
    var fullPath = String.format("%s/graphql/%s/%s.graphql", baseResourcePath(), requestType, extensionlessFileName);
    var resource = new ClassPathResource(fullPath);
    try {
      var operation = Map.of(
        "query", new String(resource.getInputStream().readAllBytes())
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
    try {
      var resource = new ClassPathResource(String.format("%s/%s", baseResourcePath(), fileName));
      var expectedJson = new String(resource.getInputStream().readAllBytes());
      return new JsonMatcher(expectedJson);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  default String baseResourcePath() {
    return String
      .format("%s.%s", getClass().getPackageName(), getClass().getSimpleName().toLowerCase())
      .replaceAll("\\.", "/");
  }

  @RequiredArgsConstructor
  class JsonMatcher extends TypeSafeMatcher<String> {

    private static final JSONComparator JSON_COMPARATOR = new DefaultComparator(JSONCompareMode.LENIENT);

    private final String expectedJson;
    private JSONCompareResult result;

    @Override
    public boolean matchesSafely(String item) {
      try {
        result = JSONCompare.compareJSON(expectedJson, item, JSON_COMPARATOR);
        return result.passed();
      } catch (JSONException e) {
        throw new IllegalArgumentException(e);
      }
    }

    @Override
    public void describeTo(Description description) {
      if (result != null) {
        description.appendText(result.getMessage());
      } else {
        description.appendText("Valid JSON");
      }
    }

  }

}
