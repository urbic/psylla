![Psylla logo](src/logo/psylla.svg)

# Psylla

**Psylla** is a scriptable interpretive concatenative homoiconic
PostScript-like programming language.

The syntax of the language is based on postfix (reverse Polish) notation.

Psylla is implemented as a library written in Java.

[![Tag](https://shields.io/github/v/tag/urbic/psylla)](tags)
[![License](https://img.shields.io/badge/license-zlib%2Fpng-blue.svg)](https://opensource.org/licenses/Zlib)
[![Java CI Build Status](https://github.com/urbic/psylla/actions/workflows/ci.yml/badge.svg)](https://github.com/urbic/psylla/actions/workflows/ci.yml)
[![OBS Build Result](https://build.opensuse.org/projects/home:urbic:coneforest/packages/psylla/badge.svg?type=percent)](https://build.opensuse.org/package/show/home:urbic:coneforest/psylla)

---

## Features

* Dynamic arrays, strings, dictionaries, sets and streams
* Arbitrary-precision rational arithmetic
* Real and complex numbers
* Multicontext execution (multithreading), synchronization primitives, blocking
  queues
* Regular expressions _(under construction)_
* REPL
* Support for Java scripting API (JSR 223)

## Examples

Here are three implementations of the procedure that calculates the factorial:
recursive, iterative and stream-based.

```
/factorial
{
    dup iszero
    { pop 1 }
    { dup 1 sub factorial mul }
    ifelse
}
bind def
```

```
/factorial
{
    1 2 1 4 -1 roll { mul } for
}
bind def
```

```
/factorial
{
    1 1 3 -1 roll range 1 { mul } reduce
}
bind def
```

Calculating factorial of 50:

```
50 factorial toname say
```

More sample programs can be found in the dedicated
[directory](src/examples/psylla).

## Dependencies

### Jar:

Java 17 (OpenJDK or Oracle JDK), JavaCC, Apache Ant, Apache Ivy, JUnit 4, JLine 1

### Documentation:

Apache Ant, Apache Ivy, Docbook 5 XSL, Saxon HE 6, XSLTHL, SassC, Graphviz
