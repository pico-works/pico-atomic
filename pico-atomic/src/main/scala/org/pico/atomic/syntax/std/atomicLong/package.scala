package org.pico.atomic.syntax.std

import java.util.concurrent.atomic.AtomicLong

import scala.annotation.tailrec

package object atomicLong {
  implicit class AtomicLongOps_YYKh2cf(val self: AtomicLong) extends AnyVal {
    /** Repeatedly attempt to update the reference using the update function f
      * until the condition is satisfied and is able to set it atomically.
      * @param f The function to transform the current reference
      * @param cond The value predicate
      * @return An old value and a new value if an update happened
      */
    @inline
    final def updateIf(cond: Long => Boolean, f: Long => Long): Option[(Long, Long)] = {
      @tailrec
      def go(): Option[(Long, Long)] = {
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
    final def update(f: Long => Long): (Long, Long) = {
      updateIf(_ => true, f).get //Safe to call .get by construction, the predicate is hardcoded to be true
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
