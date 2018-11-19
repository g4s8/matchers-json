# matchers-json
Hamcrest matchers for json objects

[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/g4s8/matchers-json)](http://www.rultor.com/p/g4s8/matchers-json)

[![Bintray](https://api.bintray.com/packages/g4s8/mvn/com.g4s8.matchers-json/images/download.svg)](https://bintray.com/g4s8/mvn/com.g4s8.matchers-json/_latestVersion)
[![Build Status](https://img.shields.io/travis/g4s8/matchers-json.svg?style=flat-square)](https://travis-ci.org/g4s8/matchers-json)
[![Build status](https://ci.appveyor.com/api/projects/status/ahhde7mposa3ra9w?svg=true)](https://ci.appveyor.com/project/g4s8/matchers-json)
[![PDD status](http://www.0pdd.com/svg?name=g4s8/matchers-json)](http://www.0pdd.com/p?name=g4s8/matchers-json)
[![License](https://img.shields.io/github/license/g4s8/matchers-json.svg?style=flat-square)](https://github.com/g4s8/matchers-json/blob/master/LICENSE)
[![Test Coverage](https://img.shields.io/codecov/c/github/g4s8/matchers-json.svg?style=flat-square)](https://codecov.io/github/g4s8/matchers-json?branch=master)


## Get

To install it add dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>com.g4s8</groupId>
  <artifactId>matchers-json</artifactId>
  <version></version>
</dependency>
```
you can find latest version on Bintray badge above.

## Usage

### Matching JSON objects
To match any field in JSON object use `JsonHas`:
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

### Matching JSON arrays

To match JSON array use `JsonContains` class with list of matchers for items as argument:
```java
new JsonContains(
    new JsonValueIs("foo"),
    new JsonValueIs(42),
    new JsonValueIs(true),
    JsonValueIs.NULL
)
```

Also you can use `JsonHas` matcher to match JSON objects in array:
```java
new JsonContains(
    new JsonHas("value", new JsonValueIs(1234)),
    new JsonHas("value", new JsonValueIs(6532))
)
```

or use `JsonContains` as `JsonHas` argument to match an array in JSON object field:
```java
new JsonHas(
    "items",
    new JsonContains(
        new JsonValueIs(1),
        new JsonValueIs(2),
        new JsonValueIs(3)
    )
)
```

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

## Additional

Also this library provides some useful classes to help you convert different types to JSON matchers:

`StringIsJson` is decorator for JSON matcher which implements `Matcher<String>` interface,
so you can match a string against JSON matchers:
```java
MatcherAssert.assertThat(
    "{\"foo\":\"bar\"}",
    new StringIsJson(new JsonHas("foo", new JsonValueIs("bar")))
);
``` 

## Contribution
 1. Fork the repo
 2. Make changes
 3. Run `mvn clean install -Pqulice`
 4. Submit a pull request

Keep in mind that PR will not be accepted if it doesn't pass unit tests and [Qulice](https://www.qulice.com/) checks.

If something is not clear to you or documentation is missed, please submit a bug.

If something is not working, please submit a bug.