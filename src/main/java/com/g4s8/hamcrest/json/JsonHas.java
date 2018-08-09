package com.g4s8.hamcrest.json;

import javax.json.JsonObject;
import javax.json.JsonValue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @since
 */
public final class JsonHas extends TypeSafeMatcher<JsonObject> {

    /**
     * Field name.
     */
    private final String field;

    /**
     * Value matcher.
     */
    private final Matcher<? extends JsonValue> matcher;

    /**
     * Ctor.
     *
     * @param field Field name
     * @param matcher Value matcher
     */
    public JsonHas(final String field,
        final Matcher<? extends JsonValue> matcher) {
        this.field = field;
        this.matcher = matcher;
    }

    protected boolean matchesSafely(final JsonObject item) {
        return this.matcher.matches(item.get(this.field));
    }

    public void describeTo(final Description description) {
        description.appendText("field ")
            .appendValue(this.field)
            .appendText(" with ")
            .appendDescriptionOf(this.matcher);
    }

    @Override
    protected void describeMismatchSafely(final JsonObject item,
        final Description desc) {
        desc.appendText("field ")
            .appendValue(this.field)
            .appendText(" ");
        this.matcher.describeMismatch(item.get(this.field), desc);
    }
}
