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
    @inline
    final def update(f: Long => Long): (Long, Long) = {
      @tailrec
      def go(): (Long, Long) = {
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

    /** Atomically swap a value for the existing value in an AtomicLong.  Same as getAndSet.
      *
      * @param newValue The new value to atomically swap into the AtomicLong
      * @return The old value that was swapped out.
      */
    @inline
    final def swap(newValue: Long): Long = self.getAndSet(newValue)

    /** Get the value
      *
      * @return The value
      */
    @inline
    final def value: Long = self.get
  }
}
