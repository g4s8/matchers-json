package com.g4s8.hamcrest.json;

import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for json matchers.
 *
 * @since 1.0
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
            Json.createObjectBuilder().add(key, Boolean.TRUE).build(),
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
}
