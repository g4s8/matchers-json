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
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestCase;
import wtf.g4s8.oot.TestGroup;

/**
 * Test case for {@link JsonContains}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocParameterOrderCheck (500 lines)
 */
public final class JsonContainsCase extends TestGroup.Wrap {

    /**
     * Ctor.
     */
    public JsonContainsCase() {
        super(
            new TestGroup.Of(
                "json-contains",
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
    private static final class SimpleItemsTest extends TestCase.Wrap {
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
                new SimpleTest<JsonArray>(
                    "simple items",
                    () -> Json.createArrayBuilder()
                        .add(str)
                        .add(num)
                        .add(JsonValue.TRUE)
                        .add(JsonValue.NULL)
                        .build(),
                    new JsonContains(
                        new JsonValueIs(str),
                        new JsonValueIs(num),
                        new JsonValueIs(true),
                        JsonValueIs.NULL
                    )
                )
            );
        }
    }

    /**
     * Test case against nested objects json array items.
     * @since 1.0
     */
    private static final class NestedObjectTest extends TestCase.Wrap {
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
                new SimpleTest<JsonArray>(
                    "nested objects",
                    () -> Json.createArrayBuilder()
                        .add(Json.createObjectBuilder().add(key, val))
                        .build(),
                    new JsonContains(new JsonHas(key, new JsonValueIs(val)))
                )
            );
        }
    }

    /**
     * Test case against complex JSON arrays and objects mixed together.
     * @since 1.0
     */
    private static final class ComplexHierarchyTest extends TestCase.Wrap {

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
                new SimpleTest<JsonArray>(
                    "complex hierarchy",
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
                    ).build(),
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
                    )
                )
            );
        }
    }
}
