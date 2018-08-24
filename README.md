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
