package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger, AtomicLong, AtomicReference}

import org.specs2.mutable.Specification
import org.pico.atomic.syntax.std.atomicBoolean._
import org.pico.atomic.syntax.std.atomicInteger._
import org.pico.atomic.syntax.std.atomicLong._
import org.pico.atomic.syntax.std.atomicReference._

class PerformanceSpec extends Specification {
  "Ensure performance for AtomicReference" in {
    val atomicReference = new AtomicReference(0L)

    val threadCount = 10

    val threads = (0 until threadCount).map { threadId =>
      val thread = new Thread {
        override def run(): Unit = {
          for (j <- 0L until 1000L) {
            atomicReference.update(_ + 1L)
          }
        }
      }

      thread.start()

      thread
    }

    val deadline = System.currentTimeMillis() + 1000

    threads.foreach(_.join((deadline - System.currentTimeMillis()) max 0, 0))

    threads.foreach { thread =>
      thread.getState must_=== Thread.State.TERMINATED
    }

    ok
  }

  "Ensure performance for AtomicLong" in {
    val atomicLong = new AtomicLong(0L)

    val threadCount = 10

    val threads = (0 until threadCount).map { threadId =>
      val thread = new Thread {
        override def run(): Unit = {
          for (j <- 0L until 1000L) {
            atomicLong.update(_ + 1L)
          }
        }
      }

      thread.start()

      thread
    }

    val deadline = System.currentTimeMillis() + 1000

    threads.foreach(_.join((deadline - System.currentTimeMillis()) max 0, 0))

    threads.foreach { thread =>
      thread.getState must_=== Thread.State.TERMINATED
    }

    ok
  }

  "Ensure performance for AtomicInteger" in {
    val atomicInteger = new AtomicInteger(0)

    val threadCount = 10

    val threads = (0 until threadCount).map { threadId =>
      val thread = new Thread {
        override def run(): Unit = {
          for (j <- 0L until 1000L) {
            atomicInteger.update(_ + 1)
          }
        }
      }

      thread.start()

      thread
    }

    val deadline = System.currentTimeMillis() + 1000

    threads.foreach(_.join((deadline - System.currentTimeMillis()) max 0, 0))

    threads.foreach { thread =>
      thread.getState must_=== Thread.State.TERMINATED
    }

    ok
  }


  "Ensure performance for AtomicBoolean" in {
    val atomicBoolean = new AtomicBoolean(false)

    val threadCount = 10

    val threads = (0 until threadCount).map { threadId =>
      val thread = new Thread {
        override def run(): Unit = {
          for (j <- 0L until 1000L) {
            atomicBoolean.update(!_)
          }
        }
      }

      thread.start()

      thread
    }

    val deadline = System.currentTimeMillis() + 1000

    threads.foreach(_.join((deadline - System.currentTimeMillis()) max 0, 0))

    threads.foreach { thread =>
      thread.getState must_=== Thread.State.TERMINATED
    }

    ok
  }
}
