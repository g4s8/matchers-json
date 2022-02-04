[![Build and test](https://github.com/g4s8/matchers-json/actions/workflows/maven.yml/badge.svg)](https://github.com/g4s8/matchers-json/actions/workflows/maven.yml)
[![Maven Central](https://img.shields.io/maven-central/v/wtf.g4s8/matchers-json.svg)](https://maven-badges.herokuapp.com/maven-central/wtf.g4s8/matchers-json)

[Hamcrest matchers](http://hamcrest.org/JavaHamcrest/) for `javax.json` objects and arrays, and raw JSON strings.

[![PDD status](http://www.0pdd.com/svg?name=g4s8/matchers-json)](http://www.0pdd.com/p?name=g4s8/matchers-json)
[![License](https://img.shields.io/github/license/g4s8/matchers-json.svg?style=flat-square)](https://github.com/g4s8/matchers-json/blob/master/LICENSE)
[![Hits-of-Code](https://hitsofcode.com/github/g4s8/matchers-json)](https://hitsofcode.com/view/github/g4s8/matchers-json)
[![Test Coverage](https://img.shields.io/codecov/c/github/g4s8/matchers-json.svg?style=flat-square)](https://codecov.io/github/g4s8/matchers-json?branch=master)



## Install

Add dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>wtf.g4s8</groupId>
  <artifactId>matchers-json</artifactId>
  <version><!-- latest version --></version>
</dependency>
```
you can find latest version on Bintray badge above.
Also, this library depends on `javax.json:javax.json-api` library.

## Example

```java
MatcherAssert.assertThat(
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
```

## Usage

There are 3 main matchers for JSON strucutres:
 - `JsonHas` - matcher for JSON objects
 - `JsonContains` - matcher for JSON arrays
 - `JsonValueIs` - matcher for JSON values (object or array items)

### Matching JsonValues

`JsonHas` and `JsonContains` has many overloaded constructors for most common scenarios, in many cases
you don't need `JsonValueIs` matchers. But it's the core element of this library.

`JsonValueIs` implements matcher of type `Matcher<JsonValue>`. JSON defines these types of values:
 - Array
 - Object
 - String
 - Number
 - True
 - False
 - Null

`JsonValueIs` has standard constructors for `String`, `Number`, `True` and `False` types:
 - `JsonValueIs(Matcher<String>)` - for custom string matching.
 - `JsonValueIs(String)` - for exact string matching (same as `new JsonValueIs(Matchers.equalTo(value))`).
 - `JsonValueIs(Number)` - matcher for `java.lang.Number` and its subclasses (`Integer`, `Long`, `Double`, etc).
 - `JsonValueIs(boolean)` - matcher for `True` and `False` type depends on `boolean` value.

Also, it has constant for `Null` matcher: `JsonValueIs.NULL`.

`JsonValueIs` has two constructors for generic value matching:
 - `JsonValueIs(Matcher<JsonValue.ValueType>, Matcher<String>)` with type matcher
 for value type and value matcher of value string representation.
 - `JsonValueIs(JsonValue.ValueType, Matcher<String> matcher)` - simplified version,
 same as `new JsonValueIs(Matchers.equalTo(type), value)`.

These constructors could be used for general purpose value matching, for complex matching logic or for creating
complex custom matchers.

### Matching JSON objects

Object fields could be matched with `JsonHas` matcher (implements `Matcher<JsonObject>`): it takes field name and value matcher
as arguments and checks that object has field with name specified and value for this field could
be checked with value-matcher. It has common overloaded constructors to avoid redundant `JsonValueIs` usage:
 - `JsonHas(String, String)` - for exact string value matching
 - `JsonHas(String, Number)` - for exact number value matching
 - `JsonHas(String, boolean)` - for exact boolean number matching

And it has full matching constructors for `JsonValue` matcher:
 - `JsonHas(String, Matcher<? extends JsonValue>)` - matches object's fields against custom matcher,
 where matcher could be `JsonValueIs`, or another nested `JsonHas`, or `JsonContains` (for arrays),
 or custom matcher of this type.

*Examples:*

```java
// Check JSON object has field with key `foo` and string value `bar`
new JsonHas("foo", "bar");

// Check JSON object has another nested JSON object with matchers provided
new JsonHas("nested", new JsonHas(valueMatchers));

// Check JSON object has field with array
new JsonHas("arr", new JsonContains(itemsMatchers));
```

### Matching JSON arrays

JSON array items could be matched with `JsonContains` matcher. It implements `Matcher<JsonArray>`.

It has simple constructors for matching arrays with same item's primitive types:
 - `JsonContains(Number...)` - for number items
 - `JsonContains(String...)` - for string items
 - `JsonContains(Boolean...)` - for boolean items

To match dynamic array of different types use:
 - `JsonContains(Matcher<? extends JsonValue>...)`
 - `JsonContains(List<Matcher<? extends JsonValue>>)`

E.g. to verify this json array:
```json
[ "foo", 42, true, null ]
```
Use this matcher:
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

### Adapters

Also, this library provides some useful classes to help you convert different types to JSON matchers:

`StringIsJson` is a decorator for JSON matcher which implements `Matcher<String>` interface,
so you can match a string against JSON matchers:
```java
MatcherAssert.assertThat(
    "{\"foo\":\"bar\"}",
    new StringIsJson(new JsonHas("foo", new JsonValueIs("bar")))
);
``` 
