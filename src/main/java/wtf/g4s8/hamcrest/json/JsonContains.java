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

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher against JSON array.
 *
 * @since 0.2
 * @todo #2:30min Implement `describeMismatchSafely` to provide more details
 *  on mismatch, this method should find which matcher did fail and
 *  append to description failing matcher description and item which
 *  caused mismatch.
 */
public final class JsonContains extends TypeSafeMatcher<JsonArray> {

    /**
     * Matchers.
     */
    private final List<Matcher<? extends JsonValue>> matchers;

    /**
     * Match JSON array with matchers arguments.
     *
     * @param matchers Matchers
     */
    @SafeVarargs
    public JsonContains(final Matcher<? extends JsonValue>... matchers) {
        this(Arrays.asList(matchers));
    }

    /**
     * Match json array agains matcher list.
     *
     * @param matchers List of matchers
     */
    public JsonContains(final List<Matcher<? extends JsonValue>> matchers) {
        super();
        this.matchers = Collections.unmodifiableList(matchers);
    }

    /**
     * Match provided numbers.
     *
     * @param nums Numbers
     */
    public JsonContains(final Number... nums) {
        this(
            Stream.of(nums).map(JsonValueIs::new).collect(Collectors.toList())
        );
    }

    /**
     * Match provided strings.
     *
     * @param strs Strings
     */
    public JsonContains(final String... strs) {
        this(
            Stream.of(strs).map(JsonValueIs::new).collect(Collectors.toList())
        );
    }

    /**
     * Match provided booleans.
     *
     * @param bools Booleans
     */
    public JsonContains(final Boolean... bools) {
        this(
            Stream.of(bools).map(JsonValueIs::new).collect(Collectors.toList())
        );
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(
            String.format("JSON array with %d items", this.matchers.size())
        );
    }

    @Override
    public boolean matchesSafely(final JsonArray item) {
        boolean matches = true;
        if (item.size() == this.matchers.size()) {
            for (int pos = 0; pos < this.matchers.size(); ++pos) {
                if (!this.matchers.get(pos).matches(item.get(pos))) {
                    matches = false;
                    break;
                }
            }
        } else {
            matches = false;
        }
        return matches;
    }
}
