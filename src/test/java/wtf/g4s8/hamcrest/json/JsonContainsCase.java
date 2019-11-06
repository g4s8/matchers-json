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

import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import org.hamcrest.Matchers;
import wtf.g4s8.oot.SimpleRun;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestChain;
import wtf.g4s8.oot.TestRun;

/**
 * Test case for {@link JsonContains}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocParameterOrderCheck (500 lines)
 */
public final class JsonContainsCase extends TestRun.Wrap {

    /**
     * Ctor.
     */
    public JsonContainsCase() {
        super(
            new TestChain(
                new SimpleItemsTest(),
                new NestedObjectTest(),
                new ComplexHierarchyTest()
            )
        );
    }

    /**
     * Simple test case against array items.
     * @since 1.0
     */
    private static final class SimpleItemsTest extends TestRun.Wrap {
        /**
         * Default ctor.
         */
        SimpleItemsTest() {
            this(1, "qwe");
        }

        /**
         * Primary ctor.
         */
        private SimpleItemsTest(final int num, final String str) {
            super(
                new SimpleRun<>(
                    new SimpleTest<JsonArray>(
                        "json contains simple items",
                        new JsonContains(
                            new JsonValueIs(str),
                            new JsonValueIs(num),
                            new JsonValueIs(true),
                            JsonValueIs.NULL
                        ),
                        () -> Json.createArrayBuilder()
                            .add(str)
                            .add(num)
                            .add(JsonValue.TRUE)
                            .add(JsonValue.NULL)
                            .build()
                    )
                )
            );
        }
    }

    /**
     * Test case against nested objects json array items.
     * @since 1.0
     */
    private static final class NestedObjectTest extends TestRun.Wrap {
        /**
         * Default ctor.
         */
        NestedObjectTest() {
            this("foo", "bar");
        }

        /**
         * Primary ctor.
         */
        private NestedObjectTest(final String key, final String val) {
            super(
                new SimpleRun<>(
                    new SimpleTest<JsonArray>(
                        "json contains nested objects",
                        new JsonContains(new JsonHas(key, new JsonValueIs(val))),
                        () -> Json.createArrayBuilder()
                            .add(Json.createObjectBuilder().add(key, val))
                            .build()
                    )
                )
            );
        }
    }

    /**
     * Test case against complex JSON arrays and objects mixed together.
     * @since 1.0
     */
    private static final class ComplexHierarchyTest extends TestRun.Wrap {

        /**
         * Default ctor.
         */
        ComplexHierarchyTest() {
            this("name", "values", "value", "second", "first");
        }

        /**
         * Primary ctor.
         * @checkstyle ParameterNumberCheck (5 lines)
         */
        private ComplexHierarchyTest(final String kname, final String kvalues,
            final String kvalue, final String vsecond, final String vfirst) {
            super(
                new SimpleRun<>(
                    new SimpleTest<JsonArray>(
                        "json contains complex hierarchy",
                        new JsonContains(
                            Matchers.allOf(
                                Arrays.asList(
                                    new JsonHas(kname, new JsonValueIs(vfirst)),
                                    new JsonHas(
                                        kvalues,
                                        new JsonContains(
                                            new JsonHas(kvalue, new JsonValueIs(1))
                                        )
                                    )
                                )
                            ),
                            Matchers.allOf(
                                Arrays.asList(
                                    new JsonHas(kname, new JsonValueIs(vsecond)),
                                    new JsonHas(
                                        kvalues,
                                        new JsonContains(
                                            new JsonHas(kvalue, new JsonValueIs(1)),
                                            new JsonHas(kvalue, new JsonValueIs(2))
                                        )
                                    )
                                )
                            )
                        ),
                        () -> Json.createArrayBuilder()
                            .add(
                                Json.createObjectBuilder()
                                    .add(kname, vfirst)
                                    .add(
                                        kvalues,
                                        Json.createArrayBuilder()
                                            .add(
                                                Json.createObjectBuilder()
                                                    .add(kvalue, 1)
                                            )
                                    )
                            ).add(
                            Json.createObjectBuilder()
                                .add(kname, vsecond)
                                .add(
                                    kvalues,
                                    Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder().add(kvalue, 1))
                                        .add(Json.createObjectBuilder().add(kvalue, 2))
                                )
                        ).build()
                    )
                )
            );
        }
    }
}
