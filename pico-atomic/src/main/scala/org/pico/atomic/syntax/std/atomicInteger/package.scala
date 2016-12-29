package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicInteger

import scala.annotation.tailrec

package object atomicInteger {
  implicit class AtomicIntegerOps_YYKh2cf(val self: AtomicInteger) extends AnyVal {
    /** Repeatedly attempt to update the reference using the update function f
      * until the condition is satisfied and is able to set it atomically.
      * @param f The function to transform the current reference
      * @param cond The value predicate
      * @return An old value and a new value if an update happened
      */
    @inline
    final def updateIf(cond: Int => Boolean, f: Int => Int): Option[(Int, Int)] = {
      @tailrec
      def go(): Option[(Int, Int)] = {
        val oldValue = self.get()
        val isOk = cond(oldValue)
      
        if (!isOk) None
        else {
          val newValue = f(oldValue)
          if (self.compareAndSet(oldValue, newValue)) Some(oldValue -> newValue)
          else go()
        }
      }
      go()
    }
    
    /** Repeatedly attempt to update the value using the update function f until able to
      * set it atomically.
      * @param f The function to transform the current value
      * @return A pair of the old and new values
      */
    @inline
    final def update(f: Int => Int): (Int, Int) = {
      updateIf(_ => true, f).get //Safe to call .get by construction, the predicate is hardcoded to be true
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
