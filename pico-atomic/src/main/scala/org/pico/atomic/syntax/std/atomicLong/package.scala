package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicLong

import scala.annotation.tailrec

package object atomicLong {
  implicit class AtomicLongOps_YYKh2cf(val self: AtomicLong) extends AnyVal {
    /** Repeatedly attempt to update the value using the update function f until able to
      * set it atomically.
      * @param f The function to transform the current value
      * @return A pair of the old and new values
      */
    def update(f: Long => Long): (Long, Long) = {
      @tailrec
      def go(oldValue: Long): (Long, Long) = {
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
