# pico-atomic
[![CircleCI](https://circleci.com/gh/pico-works/pico-atomic/tree/develop.svg?style=svg)](https://circleci.com/gh/pico-works/pico-atomic/tree/develop)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b3d7075c42e74cb2b5d36b88239655e6)](https://www.codacy.com/app/newhoggy/pico-works-pico-atomic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pico-works/pico-atomic&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b3d7075c42e74cb2b5d36b88239655e6)](https://www.codacy.com/app/newhoggy/pico-works-pico-atomic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pico-works/pico-atomic&amp;utm_campaign=Badge_Coverage)
[![Gitter chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/pico-works/general)

Support library for atomic operations.

## Getting started

Add this to your SBT project:

```
resolvers += "dl-john-ky-releases" at "http://dl.john-ky.io/maven/releases"

libraryDependencies += "org.pico" %%  "pico-atomic" % "0.2.2"
```

## Atomic updates
A value can be updated atomically with a transformation function using the update method:

      import org.pico.atomic.syntax.std.atomicInteger._
      import java.util.concurrent.atomic.AtomicInteger
      val ref = new AtomicInteger(1)
      ref.update(_ + 1) must_=== (1, 2)
      ref.value must_=== 2

      import org.pico.atomic.syntax.std.atomicReference._
      import java.util.concurrent.atomic.AtomicReference
      val ref = new AtomicReference[Int](1)
      ref.update(_ + 1) must_=== (1, 2)
      ref.value must_=== 2

The update function will retry applying the transformation function until a successful
atomic update occurs.

## Swap method
The `swap` method is equivalent to `getAndSet`, just with a shorter name.
