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

import javax.json.Json;
import javax.json.JsonValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for json matchers.
 *
 * @since 0.1
 * @todo #1:30min Add matchers for json-arrays, it should be similar
 *  to hamcrest matchers for collections/lists, it may be named like
 *  `JsonArrayContains`, `JsonArrayHas` etc.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class JsonHasTest {

    @Test
    public void matchJsonField() throws Exception {
        final String foo = "foo";
        final String bar = "bar";
        final Integer num = 42;
        MatcherAssert.assertThat(
            Json.createObjectBuilder()
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
        );
    }

    @Test
    public void matchBoolean() throws Exception {
        final String key = "key";
        MatcherAssert.assertThat(
            Json.createObjectBuilder().add(key, JsonValue.TRUE).build(),
            new JsonHas(
                key,
                new JsonValueIs(Boolean.TRUE)
            )
        );
    }

    @Test
    public void matchString() throws Exception {
        final String key = "qwe";
        final String value = "one two three";
        MatcherAssert.assertThat(
            Json.createObjectBuilder().add(key, value).build(),
            new JsonHas(
                key,
                new JsonValueIs(value)
            )
        );
    }

    @Test
    public void matchNull() throws Exception {
        final String key = "asd";
        MatcherAssert.assertThat(
            Json.createObjectBuilder().add(key, JsonValue.NULL).build(),
            new JsonHas(
                key,
                JsonValueIs.NULL
            )
        );
    }
}
