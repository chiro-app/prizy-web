package io.prizy.test.json;

import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

/**
 * @author Nidhal Dogga
 * @created 16/08/2022 10:17
 */

@RequiredArgsConstructor
public class JSONMatcher extends TypeSafeMatcher<String> {

  private final String expectedJson;
  private final JSONCompareMode mode;
  private JSONCompareResult result;

  @Override
  public boolean matchesSafely(String item) {
    var comparator = new RegexJSONComparator(mode);
    try {
      result = JSONCompare.compareJSON(expectedJson, item, comparator);
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
