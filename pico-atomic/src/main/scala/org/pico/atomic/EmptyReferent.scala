package org.pico.atomic

/** This type class defines the value that represents the empty referent for the given type R.
  *
  * @tparam R The type of the referent
  */
trait EmptyReferent[R] {
  /** Get the empty referent for a given type R.
    *
    * When AtomicReference.release is called on an atomic reference of the type R, this value is
    * substituted in its place.
    * @return The empty referent.
    */
  def emptyReferent: R
}

object EmptyReferent {
  /** Get the empty referent for a given type R
    * @tparam R The type of the empty referent
    * @return The empty referent.
    */
  @inline
  final def apply[R: EmptyReferent]: R = implicitly[EmptyReferent[R]].emptyReferent

  /** Define an instance of the EmptyReferent type class
    * @param referent The value to use as the empty referent
    * @return New EmptyReferent type class.
    */
  @inline
  final def define[R](referent: R): EmptyReferent[R] = new EmptyReferent[R] {
    override def emptyReferent: R = referent
  }
}
