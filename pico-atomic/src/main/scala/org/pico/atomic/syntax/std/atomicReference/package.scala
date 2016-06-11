package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

package object atomicReference {
  implicit class AtomicReferenceOps_YYKh2cf[A](val self: AtomicReference[A]) extends AnyVal {
    /** Repeatedly attempt to update the reference using the update function f until able to
      * set it atomically.
      * @param f The function to transform the current reference
      * @return A pair of the old and new values
      */
    @inline
    final def update(f: A => A): (A, A) = {
      @tailrec
      def go(oldValue: A): (A, A) = {
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

    /** Atomically swap a value for the existing value in an atomic reference.  Same as getAndSet.
      *
      * @param newValue The new value to atomically swap into the atomic reference
      * @return The old value that was swapped out.
      */
    @inline
    final def swap(newValue: A): A = self.getAndSet(newValue)
  }
}
