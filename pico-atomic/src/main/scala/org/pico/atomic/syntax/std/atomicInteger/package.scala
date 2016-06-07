package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicInteger

import scala.annotation.tailrec

package object atomicInteger {
  implicit class AtomicIntegerOps_YYKh2cf(val self: AtomicInteger) extends AnyVal {
    /** Repeatedly attempt to update the value using the update function f until able to
      * set it atomically.
      * @param f The function to transform the current value
      * @return A pair of the old and new values
      */
    def update(f: Int => Int): (Int, Int) = {
      @tailrec
      def go(oldValue: Int): (Int, Int) = {
        val newValue = f(oldValue)
        val currentValue = self.getAndSet(newValue)

        if (currentValue != oldValue) {
          go(currentValue)
        } else {
          (oldValue, newValue)
        }
      }

      go(self.get())
    }
  }
}
