package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicBoolean

import scala.annotation.tailrec

package object atomicBoolean {
  implicit class AtomicBooleanOps_YYKh2cf(val self: AtomicBoolean) extends AnyVal {
    /** Repeatedly attempt to update the value using the update function f until able to
      * set it atomically.
      * @param f The function to transform the current value
      * @return A pair of the old and new values
      */
    def update(f: Boolean => Boolean): (Boolean, Boolean) = {
      @tailrec
      def go(oldValue: Boolean): (Boolean, Boolean) = {
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

    /** Atomically swap a value for the existing value in an AtomicBoolean.  Same as getAndSet.
      *
      * @param newValue The new value to atomically swap into the AtomicBoolean
      * @return The old value that was swapped out.
      */
    @inline
    final def swap(newValue: Boolean): Boolean = self.getAndSet(newValue)

    /** Get the value
      *
      * @return The value
      */
    @inline
    final def value: Boolean = self.get
  }
}
