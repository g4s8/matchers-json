package wtf.g4s8.hamcrest.json;

import javax.json.Json;
import org.hamcrest.Matchers;
import wtf.g4s8.oot.SimpleTest;
import wtf.g4s8.oot.TestGroup;

/**
 * Examples from README doc.
 */
final class ReadmeExamplesCase extends TestGroup.Wrap {
    public ReadmeExamplesCase() {
        super(
            new TestGroup.Of(
                new SimpleTest<>(
                    "Main example from readme works",
                    // example json object
                    Json.createObjectBuilder().add(
                        "response", // with nested object
                        Json.createObjectBuilder()
                        .add("result", 42) // with string
                        .add("constant", true) // with bool
                        .add("description", "result of 40 + 2")
                        .add("function", Json.createObjectBuilder() // with nested object
                            .add("name", "sum")
                            .add("args", Json.createArrayBuilder().add(40).add(2)))) // with nested array
                        .build(),
                    new JsonHas("response", Matchers.allOf(
                        new JsonHas("constant", true), // match object in any order
                        new JsonHas("result", 42), // match exact value - same as Matchers.equalTo(42)
                        new JsonHas("description", new JsonValueIs(Matchers.stringContainsInOrder("result", "40 + 2"))), // match with matcher
                        new JsonHas("function", Matchers.allOf( // match all fields of nested object
                            new JsonHas("name", "sum"), // exact match
                            new JsonHas("args",  new JsonContains(40, 2)))))) // match nested array (ordered matcher)
                )
            )
        );
    }
}
