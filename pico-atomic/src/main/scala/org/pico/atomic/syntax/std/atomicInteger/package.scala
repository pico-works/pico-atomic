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
    @inline
    final def update(f: Int => Int): (Int, Int) = {
      @tailrec
      def go(): (Int, Int) = {
        val oldValue = self.get()
        val newValue = f(self.get())

        if (self.compareAndSet(oldValue, f(oldValue))) {
          (oldValue, newValue)
        } else {
          go()
        }
      }

      go()
    }

    /** Atomically swap a value for the existing value in an AtomicInteger.  Same as getAndSet.
      *
      * @param newValue The new value to atomically swap into the AtomicInteger
      * @return The old value that was swapped out.
      */
    @inline
    final def swap(newValue: Int): Int = self.getAndSet(newValue)

    /** Get the value
      *
      * @return The value
      */
    @inline
    final def value: Int = self.get
  }
}
