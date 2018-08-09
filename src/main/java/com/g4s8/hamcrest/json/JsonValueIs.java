package com.g4s8.hamcrest.json;

import java.util.Locale;
import javax.json.JsonValue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for json value.
 *
 * @since 1.0
 */
public final class JsonValueIs extends TypeSafeMatcher<JsonValue> {

    /**
     * Matcher for json-null.
     */
    public static final TypeSafeMatcher<JsonValue> NULL =
        new JsonValueIs(
            CoreMatchers.equalTo(JsonValue.ValueType.NULL),
            CoreMatchers.any(String.class)
        );

    /**
     * Json type.
     */
    private final Matcher<JsonValue.ValueType> type;

    /**
     * Json value matcher.
     */
    private final Matcher<String> value;

    /**
     * Ctor for boolean.
     *
     * @param bool Boolean value
     */
    public JsonValueIs(final boolean bool) {
        this(
            CoreMatchers.equalTo(JsonValue.ValueType.valueOf(Boolean.toString(bool).toUpperCase(Locale.US))),
            CoreMatchers.equalTo(Boolean.toString(bool))
        );
    }

    /**
     * Ctor for number.
     *
     * @param number Expected number
     */
    public JsonValueIs(final Number number) {
        this(
            CoreMatchers.equalTo(JsonValue.ValueType.NUMBER),
            CoreMatchers.equalTo(number.toString())
        );
    }

    /**
     * Ctor for string.
     *
     * @param value Expected value
     */
    public JsonValueIs(final String value) {
        this(
            CoreMatchers.equalTo(JsonValue.ValueType.STRING),
            CoreMatchers.equalTo(value)
        );
    }

    /**
     * Ctor for string.
     *
     * @param value Json value.
     */
    public JsonValueIs(final Matcher<String> value) {
        this(CoreMatchers.equalTo(JsonValue.ValueType.STRING), value);
    }

    /**
     * Ctor.
     *
     * @param type Json type.
     * @param value Json value.
     */
    public JsonValueIs(final Matcher<JsonValue.ValueType> type,
        final Matcher<String> value) {
        this.type = type;
        this.value = value;
    }

    protected boolean matchesSafely(final JsonValue item) {
        return item != null
            && this.type.matches(item.getValueType())
            && this.value.matches(escaped(item));
    }

    public void describeTo(final Description desc) {
        desc.appendText("value ")
            .appendDescriptionOf(this.value)
            .appendText(" of type ")
            .appendDescriptionOf(this.type);
    }

    @Override
    protected void describeMismatchSafely(final JsonValue item,
        final Description desc) {
        desc.appendText("value ")
            .appendValue(escaped(item))
            .appendText(" of type ")
            .appendValue(item.getValueType());
    }

    /**
     * Escaped string.
     *
     * @param value Json value
     * @return Escaped string
     */
    private static String escaped(final JsonValue value) {
        final String esc;
        final String src = value.toString();
        if (value.getValueType().equals(JsonValue.ValueType.STRING)) {
            esc = src.substring(1).substring(0, src.length() - 2);
        } else {
            esc = src;
        }
        return esc;
    }
}
