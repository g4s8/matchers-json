/*
 * MIT License
 *
 * Copyright (c) 2018 Kirill
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights * to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package wtf.g4s8.hamcrest.json;

import jakarta.json.JsonValue;
import java.util.Locale;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches json values.
 *
 * @since 0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
            CoreMatchers.equalTo(
                JsonValue.ValueType.valueOf(Boolean.toString(bool)
                    .toUpperCase(Locale.US)
                )
            ),
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
     * @param type Value type
     * @param matcher Value matcher
     */
    public JsonValueIs(final JsonValue.ValueType type,
        final Matcher<String> matcher) {
        this(CoreMatchers.equalTo(type), matcher);
    }

    /**
     * Ctor.
     *
     * @param type Json type.
     * @param value Json value.
     */
    public JsonValueIs(final Matcher<JsonValue.ValueType> type,
        final Matcher<String> value) {
        super();
        this.type = type;
        this.value = value;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("value ")
            .appendDescriptionOf(this.value)
            .appendText(" of type ")
            .appendDescriptionOf(this.type);
    }

    @Override
    public boolean matchesSafely(final JsonValue item) {
        return item != null
            && this.type.matches(item.getValueType())
            && this.value.matches(JsonValueIs.escaped(item));
    }

    @Override
    public void describeMismatchSafely(final JsonValue item,
        final Description desc) {
        desc.appendText("value ")
            .appendValue(JsonValueIs.escaped(item))
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
