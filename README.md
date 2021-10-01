[![Build and test](https://github.com/g4s8/matchers-json/actions/workflows/maven.yml/badge.svg)](https://github.com/g4s8/matchers-json/actions/workflows/maven.yml)
[![Maven Central](https://img.shields.io/maven-central/v/wtf.g4s8/matchers-json.svg)](https://maven-badges.herokuapp.com/maven-central/wtf.g4s8/matchers-json)

[Hamcrest matchers](http://hamcrest.org/JavaHamcrest/) for JSON objects and arrays; adaptors
for other types allows you to write
[single statement unit tests](https://www.yegor256.com/2017/05/17/single-statement-unit-tests.html)
against JSON producing objects.

[![PDD status](http://www.0pdd.com/svg?name=g4s8/matchers-json)](http://www.0pdd.com/p?name=g4s8/matchers-json)
[![License](https://img.shields.io/github/license/g4s8/matchers-json.svg?style=flat-square)](https://github.com/g4s8/matchers-json/blob/master/LICENSE)
[![Hits-of-Code](https://hitsofcode.com/github/g4s8/matchers-json)](https://hitsofcode.com/view/github/g4s8/matchers-json)
[![Test Coverage](https://img.shields.io/codecov/c/github/g4s8/matchers-json.svg?style=flat-square)](https://codecov.io/github/g4s8/matchers-json?branch=master)



## Usage

Add dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>wtf.g4s8</groupId>
  <artifactId>matchers-json</artifactId>
  <version>1.0.2</version>
</dependency>
```
you can find latest version on Bintray badge above.
Also, this library depends on `javax.json:javax.json-api` library.


### Matching JSON objects

Let's assume you have an object with method:
```java
class Item {
  private final int value;
  
  Item(final int value) {
    this.value = value;
  }
  
  JsonObject json() {
    return Json.createObjectBuilder()
      .add("value", this.value)
      .build()
  }
}
```
To verify that your object returns correct value in a unit test
use `JsonHas` and `JsonValueIs` matchers:
```java
@Test()
void returnsJsonWithValue() {
  MatcherAssert.assertThat(
    new Item(value).json(),
    new JsonHas("value", 42)
);
```

### Matching JSON arrays

To match JSON array use `JsonContains` class with list of matchers for items as argument.
E.g. to verify this json array:
```json
[ "foo", 42, true, null ]
```
use this matcher:
```java
new JsonContains(
    new JsonValueIs("foo"),
    new JsonValueIs(42),
    new JsonValueIs(true),
    JsonValueIs.NULL
);
```

If your array has JSON objects:
```json
[
  { "value": 1234 },
  { "value": 6532 }
]
```
use `JsonHas` matcher inside `JsonContains`:
```java
new JsonContains(
    new JsonHas("value", new JsonValueIs(1234)),
    new JsonHas("value", new JsonValueIs(6532))
)
```

You can compose `JsonContains`, `JsonHas` and `JsonValueIs` in any combination.
E.g. if you have JSON object:
```json
{
  "items": [
    { "value": 1 },
    { "value": 2 },
    { "value": 3 }
  ]
}
```
you can use:
```java
new JsonHas(
    "items",
    new JsonContains(
        new JsonHas("value", 1),
        new JsonHas("value", 2),
        new JsonHas("value", 3)
    )
)
```
For `JsonHas` you can provide as verbose configurable matcher to constructor:
```java
// matches that JSON has a field "foo" with string value containing "bar", e.g.
// {"foo": "123-bar-456"}
new JsonHas("foo", new JsonValueIs(Matchers.stringContains("bar")));
```
as simplified version with expected values: `new JsonHas("foo", "bar")` - it's equal to
`new JsonHas("foo", new JsonValueIs(Matchers.equal("bar")))`.

### Matching JSON values

To match json value use `JsonValueIs`:

For strings: `new JsonValueIs("some string")`

For numbers: `new JsonValueIs(100)`

For booleans: `new JsonValueIs(true)`

For json-null: `JsonValueIs.NULL`

For more complex matching you can use primary constructor:
```java
MatcherAssert.assertThat(
    Json.createValue("Starting with 1 2 3"),
    new JsonValueIs(
        JsonValue.ValueType.STRING,
        Matchers.startsWith("Starting")
    )
);
```

### Adapters

Also this library provides some useful classes to help you convert different types to JSON matchers:

`StringIsJson` is a decorator for JSON matcher which implements `Matcher<String>` interface,
so you can match a string against JSON matchers:
```java
MatcherAssert.assertThat(
    "{\"foo\":\"bar\"}",
    new StringIsJson(new JsonHas("foo", new JsonValueIs("bar")))
);
``` 
