# matchers-json
Hamcrest matchers for json objects

## Usage

To match any field in json object use `JsonHas`:
```java
MatcherAssert.assertThat(
    Json.createObjectBuilder()
        .add(
            "foo",
            Json.createObjectBuilder()
              .add("bar", 42)
              .build()
        ).build(),
    new JsonHas(
        "foo",
        new JsonHas(
            "bar",
            new JsonValueIs(42)
        )
    )
);
```

To match json value use `JsonValueIs`:

For strings: `new JsonValueIs("some string")`

For numbers: `new JsonValueIs(100)`

For booleans: `new JsonValueIs(true)`

For json-null: `JsonValueIs.NULL`
