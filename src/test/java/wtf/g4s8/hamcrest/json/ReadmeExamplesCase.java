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
import org.hamcrest.Matchers;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestGroup;

/**
 * Examples from README doc.
 *
 * @since 1.5
 * @checkstyle MethodBodyCommentsCheck (100 lines)
 * @checkstyle TrailingCommentCheck (100 lines)
 * @checkstyle MagicNumberCheck (100 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ReadmeExamplesCase extends TestGroup.Wrap {

    /**
     * Ctor with readme sample test.
     */
    ReadmeExamplesCase() {
        super(
            new TestGroup.Of(
                new SimpleTest<>(
                    "Main example from readme works",
                    // example json object
                    Json.createObjectBuilder()
                        .add(
                            "response", // with nested object
                            Json.createObjectBuilder()
                                .add("result", 42) // with string
                                .add("constant", true) // with bool
                                .add("description", "result of 40 + 2")
                                .add(
                                    "function",
                                    Json.createObjectBuilder() // with nested object
                                        .add("name", "sum")
                                        .add("args", Json.createArrayBuilder().add(40).add(2))
                                )
                        ).build(), // with nested array
                    new JsonHas(
                        "response",
                        Matchers.allOf(
                            new JsonHas("constant", true), // match object in any order
                            new JsonHas("result", 42),
                            // match exact value - same as Matchers.equalTo(42)
                            new JsonHas(
                                "description",
                                new JsonValueIs(Matchers.stringContainsInOrder("result", "40 + 2"))
                            ), // match with matcher
                            new JsonHas(
                                "function",
                                Matchers.allOf(// match all fields of nested object
                                    new JsonHas("name", "sum"), // exact match
                                    new JsonHas("args", new JsonContains(40, 2))
                                )
                            )
                        )
                    ) // match nested array (ordered matcher)
                )
            )
        );
    }
}
