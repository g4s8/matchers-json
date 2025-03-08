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

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonParsingException;
import java.io.StringReader;
import java.util.function.Function;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Match a string against json matcher.
 *
 * @since 0.2
 */
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public abstract class StringIsJson extends TypeSafeMatcher<String> {

    /**
     * Json matcher.
     */
    private final Matcher<? extends JsonValue> matcher;

    /**
     * Parsing of json.
     */
    private final Function<JsonReader, JsonValue> parsing;

    /**
     * Ctor.
     *
     * @param matcher Json matcher
     * @param parsing Json parsing
     */
    private StringIsJson(
        final Matcher<? extends JsonValue> matcher,
        final Function<JsonReader, JsonValue> parsing
    ) {
        super();
        this.matcher = matcher;
        this.parsing = parsing;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("string ")
            .appendDescriptionOf(this.matcher);
    }

    @Override
    public void describeMismatchSafely(
        final String item,
        final Description description
    ) {
        description.appendText("string: '")
            .appendValue(item)
            .appendText("' ");
        try {
            this.matcher.describeMismatch(
                this.parsing.apply(Json.createReader(new StringReader(item))),
                description
            );
        } catch (final JsonParsingException err) {
            description.appendText("is not a valid json: ")
                .appendText(err.getMessage());
        }
    }

    @Override
    public boolean matchesSafely(final String item) {
        boolean success;
        try {
            success = this.matcher.matches(
                this.parsing.apply(Json.createReader(new StringReader(item)))
            );
        } catch (final JsonParsingException ignored) {
            success = false;
        }
        return success;
    }

    /**
     * Json object as string.
     * @since 0.2
     */
    public static final class Object extends StringIsJson {

        /**
         * String is JSON object.
         *
         * @param matcher Object matcher
         */
        public Object(final Matcher<JsonObject> matcher) {
            super(matcher, JsonReader::readObject);
        }
    }

    /**
     * Json array as string.
     * @since 0.2
     */
    public static final class Array extends StringIsJson {

        /**
         * String is JSON array.
         * @param matcher Array matcher
         */
        public Array(final Matcher<JsonArray> matcher) {
            super(matcher, JsonReader::readArray);
        }
    }
}
