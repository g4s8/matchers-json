package com.g4s8.hamcrest.json;

import javax.json.Json;
import javax.json.JsonValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for json matchers.
 *
 * @since 1.0
 * @todo #1:30min Add matchers for json-arrays, it should be similar
 *  to hamcrest matchers for collections/lists, it may be named like
 *  `JsonArrayContains`, `JsonArrayHas` etc.
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
        final String key = "qwe";
        MatcherAssert.assertThat(
            Json.createObjectBuilder().add(key, JsonValue.NULL).build(),
            new JsonHas(
                key,
                JsonValueIs.NULL
            )
        );
    }
}
