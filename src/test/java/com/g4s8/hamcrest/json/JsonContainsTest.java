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
package com.g4s8.hamcrest.json;

import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonValue;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link JsonContains}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class JsonContainsTest {

    @Test
    public void matchesSimpleItems() throws Exception {
        final String str = "qwerty";
        final int num = 42;
        MatcherAssert.assertThat(
            Json.createArrayBuilder()
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
        );
    }

    @Test
    public void matchesJsonObjectsInside() throws Exception {
        final String key = "foo";
        final String val = "bar";
        MatcherAssert.assertThat(
            Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add(key, val))
                .build(),
            new JsonContains(
                new JsonHas(key, new JsonValueIs(val))
            )
        );
    }

    @Test
    public void matchesComplexHierarchy() throws Exception {
        final String kname = "name";
        final String kvalues = "values";
        final String kvalue = "value";
        final String vsecond = "second";
        final String vfirst = "first";
        MatcherAssert.assertThat(
            Json.createArrayBuilder()
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
        );
    }
}
