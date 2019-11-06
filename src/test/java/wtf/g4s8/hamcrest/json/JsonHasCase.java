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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import wtf.g4s8.oot.SimpleRun;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestChain;
import wtf.g4s8.oot.TestRun;

/**
 * Test case for JSON matchers.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocParameterOrderCheck (500 lines)
 */
public final class JsonHasCase extends TestRun.Wrap {

    /**
     * Ctor.
     */
    public JsonHasCase() {
        super(
            new TestChain(
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
    private static final class JsonFields extends TestRun.Wrap {

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
                new SimpleRun<>(
                    new SimpleTest<JsonObject>(
                        "matches json items",
                        new JsonHas(
                            foo,
                            new JsonHas(
                                bar,
                                new JsonValueIs(num)
                            )
                        ),
                        () -> Json.createObjectBuilder()
                            .add(
                                foo,
                                Json.createObjectBuilder()
                                    .add(bar, num)
                                    .build()
                            ).build()
                    )
                )
            );
        }
    }

    /**
     * Test case against JSON bool items.
     * @since 1.0
     */
    private static final class MatchesBool extends TestRun.Wrap {

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
                new SimpleRun<>(
                    new SimpleTest<JsonObject>(
                        "matches bool value",
                        new JsonHas(
                            key, new JsonValueIs(Boolean.TRUE)
                        ),
                        () -> Json.createObjectBuilder().add(key, JsonValue.TRUE).build()
                    )
                )
            );
        }
    }

    /**
     * Test case against JSON string item.
     * @since 1.0
     */
    private static final class MatchesString extends TestRun.Wrap {

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
                new SimpleRun<>(
                    new SimpleTest<JsonObject>(
                        "matches string",
                        new JsonHas(
                            key, new JsonValueIs(val)
                        ),
                        () -> Json.createObjectBuilder().add(key, val).build()
                    )
                )
            );
        }
    }

    /**
     * Test case against JSON null items.
     * @since 1.0
     */
    private static final class MatchesNull extends TestRun.Wrap {
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
                new SimpleRun<>(
                    new SimpleTest<JsonObject>(
                        "matches null",
                        new JsonHas(
                            key,
                            JsonValueIs.NULL
                        ),
                        () -> Json.createObjectBuilder().add(key, JsonValue.NULL).build()
                    )
                )
            );
        }
    }
}

