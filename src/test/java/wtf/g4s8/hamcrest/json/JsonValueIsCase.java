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
import jakarta.json.JsonValue;
import org.hamcrest.Matchers;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestGroup;

/**
 * Test case for {@link JsonValueIs}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocParameterOrderCheck (500 lines)
 */
public final class JsonValueIsCase extends TestGroup.Wrap {

    /**
     * Ctor.
     */
    public JsonValueIsCase() {
        super(
            new TestGroup.Of(
                "json-value-is",
                new SimpleTest<>(
                    "matches string",
                    Json.createValue(JsonValueIsCase.class.getSimpleName()),
                    new JsonValueIs(JsonValueIsCase.class.getSimpleName())
                ),
                new SimpleTest<>(
                    "matches number",
                    Json.createValue(1),
                    new JsonValueIs(1)
                ),
                new SimpleTest<>(
                    "matches custom matchers",
                    Json.createValue("Starting with 1 2 3"),
                    new JsonValueIs(
                        JsonValue.ValueType.STRING,
                        Matchers.startsWith("Starting")
                    )
                )
            )
        );
    }
}
