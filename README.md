# pico-atomic
[![CircleCI](https://circleci.com/gh/pico-works/pico-atomic/tree/develop.svg?style=svg)](https://circleci.com/gh/pico-works/pico-atomic/tree/develop)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b3d7075c42e74cb2b5d36b88239655e6)](https://www.codacy.com/app/newhoggy/pico-works-pico-atomic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pico-works/pico-atomic&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b3d7075c42e74cb2b5d36b88239655e6)](https://www.codacy.com/app/newhoggy/pico-works-pico-atomic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pico-works/pico-atomic&amp;utm_campaign=Badge_Coverage)

Support library for atomic operations.

## Getting started

Add this to your SBT project:

```
resolvers += "dl-john-ky-releases" at "http://dl.john-ky.io/maven/releases"

libraryDependencies += "org.pico" %%  "pico-atomic" % "0.0.1-3"
```

## Atomic updates
A value can be updated atomically with a transformation function using the update method:

      import org.pico.atomic.syntax.std.atomicInteger._
      import java.util.concurrent.atomic.AtomicInteger
      val ref = new AtomicInteger(1)
      ref.update(_ + 1) must_== (1, 2)
      ref.get must_== 2

      import org.pico.atomic.syntax.std.atomicReference._
      import java.util.concurrent.atomic.AtomicReference
      val ref = new AtomicReference[Int](1)
      ref.update(_ + 1) must_== (1, 2)
      ref.get must_== 2

The update function will retry applying the transformation function until a successful
atomic update occurs.

## Releasing references
Sometimes, there is a natural value that an atomic reference should be reset to.  To reset to
that value, use the `release()` method:

    val reference: AtomicReferenc[R] = ???
    reference.release()

This will only compile for reference types`R` that have an `EmptyReferent` type class instance.

An `EmptyReferent` type class instance can easily be defined like this:

    implicit val emptyReferent_MyType_WHzeiNw = EmptyReferent.define[MyType](EmptyMyType)
