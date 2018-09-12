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

## Motivation

This library helps to achieve
[single statement unit test](https://www.yegor256.com/2017/05/17/single-statement-unit-tests.html)
rule, when testing JSON objects.

## Download

To use it add maven dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>com.g4s8</groupId>
  <artifactId>matchers-json</artifactId>
</dependency>
```
last version is: [![Bintray](https://api.bintray.com/packages/g4s8/mvn/com.g4s8.matchers-json/images/download.svg)](https://bintray.com/g4s8/mvn/com.g4s8.matchers-json/_latestVersion)

## Usage

*You can find examples in `src/test` files.*

To match a field in JSON object use `JsonHas`:
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

 - for strings: `new JsonValueIs("some string")`
 - for numbers: `new JsonValueIs(100)`
 - for booleans: `new JsonValueIs(true)`
 - for json-null: `JsonValueIs.NULL`

To match JSON array items use `JsonContains` (not implemented yet: https://github.com/g4s8/matchers-json/issues/2)
