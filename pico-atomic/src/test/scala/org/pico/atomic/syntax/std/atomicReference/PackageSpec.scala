package org.pico.atomic.syntax.std.atomicReference

import java.util.concurrent.atomic.AtomicReference

import org.specs2.mutable.Specification

class PackageSpec extends Specification {
  "AtomicReference syntax" should {
    "be able to update its reference given a transformation function" in {
      val ref = new AtomicReference[Int](1)

      ref.update(_ + 1) ==== (1, 2)
      ref.get must_=== 2
    }

    "perform updates asynchronously" in {
      val ref = new AtomicReference[Int](1)

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

    "be able to swap" in {
      val ref = new AtomicReference[Int](1)

      ref.swap(2) must_=== 1
    }
  }
}
