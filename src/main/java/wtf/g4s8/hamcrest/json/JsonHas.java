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

import javax.json.JsonObject;
import javax.json.JsonValue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches json object fields.
 *
 * @since 0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
        super();
        this.field = field;
        this.matcher = matcher;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("field ")
            .appendValue(this.field)
            .appendText(" with ")
            .appendDescriptionOf(this.matcher);
    }

    @Override
    protected boolean matchesSafely(final JsonObject item) {
        return this.matcher.matches(item.get(this.field));
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
