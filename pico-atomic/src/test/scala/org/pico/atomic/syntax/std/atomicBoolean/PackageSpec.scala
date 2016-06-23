package org.pico.atomic.syntax.std.atomicBoolean

import java.util.concurrent.atomic.AtomicBoolean

import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "AtomicBoolean syntax" should {
    "have syntax to update its reference given a transformation function" in {
      val ref = new AtomicBoolean(false)

      ref.update(!_) ==== false -> true
      ref.get must_=== true
    }

    "have syntax to update asynchronously" in {
      val ref = new AtomicBoolean(false)

      val runnable = new Runnable {
        override def run(): Unit = {
          ref.update { v =>
            Thread.sleep(10)
            !v
          }
        }
      }

      val thread1 = new Thread(runnable) { start() }
      val thread2 = new Thread(runnable) { start() }

      thread2.join()
      thread1.join()

      ref.get must_=== false
    }

    "have syntax to swap" in {
      val ref = new AtomicBoolean(false)

      ref.swap(true) must_=== false
    }
  }
}
