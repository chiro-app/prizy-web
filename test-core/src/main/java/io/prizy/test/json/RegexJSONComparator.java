package io.prizy.test.json;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.ValueMatcherException;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

/**
 * @author Nidhal Dogga
 * @created 16/08/2022 10:18
 */

public class RegexJSONComparator extends DefaultComparator {

    private final RegularExpressionValueMatcher<String> reMatcher = new RegularExpressionValueMatcher<>();

    public RegexJSONComparator(JSONCompareMode mode) {
        super(mode);
    }

    @Override
    public void compareValues(String prefix, Object expectedValue, Object actualValue, JSONCompareResult result) throws JSONException {
        if (expectedValue instanceof String && actualValue instanceof String) {
            try {
                if (!reMatcher.equal((String) actualValue, (String) expectedValue))
                    result.fail(prefix, expectedValue, actualValue);
            } catch (ValueMatcherException e) {
                result.fail(prefix, e);
            }
        } else {
            super.compareValues(prefix, expectedValue, actualValue, result);
        }
    }
}
