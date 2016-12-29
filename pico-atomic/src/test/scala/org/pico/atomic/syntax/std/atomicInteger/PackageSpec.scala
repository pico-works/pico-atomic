package org.pico.atomic.syntax.std.atomicInteger

import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}

import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "AtomicInteger" should {
    "have syntax to update its reference given a transformation function" in {
      val ref = new AtomicInteger(1)

      ref.update(_ + 1) ==== (1, 2)
      ref.get must_=== 2
    }
  
    "have syntax to update conditionally" in {
      val ref = new AtomicInteger(1)
      ref.updateIf(_ > 5, _ + 1) should beNone
      ref.updateIf(_ < 5, _ + 1) should beSome(1 -> 2)
    }

    "have syntax to update asynchronously" in {
      val ref = new AtomicInteger(1)

      val runnable = new Runnable {
        override def run(): Unit = {
          ref.update { v =>
            Thread.sleep(10)
            v + 1
          }
        }
      }

      val thread1 = new Thread(runnable) { start() }
      val thread2 = new Thread(runnable) { start() }

      thread2.join()
      thread1.join()

      ref.get must_=== 3
    }

    "have syntax to swap" in {
      val ref = new AtomicInteger(1)

      ref.swap(2) must_=== 1
    }

    "have syntax to get value" in {
      val ref = new AtomicInteger(1)

      ref.value must_=== ref.get()
    }
  }
}
