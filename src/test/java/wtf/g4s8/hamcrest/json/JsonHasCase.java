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
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestCase;
import wtf.g4s8.oot.TestGroup;

/**
 * Test case for JSON matchers.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocParameterOrderCheck (500 lines)
 */
public final class JsonHasCase extends TestGroup.Wrap {

    /**
     * Ctor.
     */
    public JsonHasCase() {
        super(
            new TestGroup.Of(
                "json-has",
                new JsonHasCase.JsonFields(),
                new JsonHasCase.MatchesString(),
                new JsonHasCase.MatchesBool(),
                new JsonHasCase.MatchesNull()
            )
        );
    }

    /**
     * Test case against JSON object fields.
     * @since 1.0
     */
    private static final class JsonFields extends TestCase.Wrap {

        /**
         * Default ctor.
         */
        JsonFields() {
            this("foo", "bar", 1);
        }

        /**
         * Primary ctor.
         */
        private JsonFields(final String foo, final String bar, final int num) {
            super(
                new SimpleTest<JsonObject>(
                    "matches json items",
                    () -> Json.createObjectBuilder()
                        .add(
                            foo,
                            Json.createObjectBuilder()
                                .add(bar, num)
                                .build()
                        ).build(),
                    new JsonHas(
                        foo,
                        new JsonHas(
                            bar,
                            new JsonValueIs(num)
                        )
                    )
                )
            );
        }
    }

    /**
     * Test case against JSON bool items.
     * @since 1.0
     */
    private static final class MatchesBool extends TestCase.Wrap {

        /**
         * Default ctor.
         */
        MatchesBool() {
            this("key");
        }

        /**
         * Primary ctor.
         */
        private MatchesBool(final String key) {
            super(
                new SimpleTest<JsonObject>(
                    "matches bool value",
                    () -> Json.createObjectBuilder().add(key, JsonValue.TRUE).build(),
                    new JsonHas(key, new JsonValueIs(Boolean.TRUE))
                )
            );
        }
    }

    /**
     * Test case against JSON string item.
     * @since 1.0
     */
    private static final class MatchesString extends TestCase.Wrap {

        /**
         * Default ctor.
         */
        MatchesString() {
            this("key123", "asdfasdberg34bf");
        }

        /**
         * Primary ctor.
         */
        private MatchesString(final String key, final String val) {
            super(
                new SimpleTest<JsonObject>(
                    "matches string",
                    () -> Json.createObjectBuilder().add(key, val).build(),
                    new JsonHas(key, new JsonValueIs(val))
                )
            );
        }
    }

    /**
     * Test case against JSON null items.
     * @since 1.0
     */
    private static final class MatchesNull extends TestCase.Wrap {
        /**
         * Default ctor.
         */
        MatchesNull() {
            this("key-null");
        }

        /**
         * Primary ctor.
         */
        private MatchesNull(final String key) {
            super(
                new SimpleTest<JsonObject>(
                    "matches null",
                    () -> Json.createObjectBuilder().add(key, JsonValue.NULL).build(),
                    new JsonHas(key, JsonValueIs.NULL)
                )
            );
        }
    }
}
