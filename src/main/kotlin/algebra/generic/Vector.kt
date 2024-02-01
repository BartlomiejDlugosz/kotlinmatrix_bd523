package algebra.generic

data class Vector<T>(val add: (T,T) -> T, val multiply: (T, T) -> T, private val v: List<T>) {
  val length = v.size
  init {
    if (v.isEmpty()) {
      throw IllegalArgumentException()
    }
  }

  operator fun get(i: Int): T {
    if (i < 0 || i >= length) return throw IndexOutOfBoundsException()
    return v[i]
  }

  operator fun plus(other: Vector<T>): Vector<T> {
    if (this.length != other.length) return throw UnsupportedOperationException()
    return Vector(this.v.mapIndexed { i, x -> add(x, other[i])
  }

  operator fun times(scalar: T): Vector<T> {
    return Vector(this.v.map{ multiply(it, scalar) })
  }

  infix fun dot(other: Vector<T>): T {
    if (this.length != other.length) return throw UnsupportedOperationException()
    return this.v.foldIndexed(0.0) { index, acc, x -> acc + multiply(x, other[index]) }
  }

  override fun toString(): String = v.toString().replace("[", "(").replace("]", ")")
}

operator fun T.times(other: Vector<T>): Vector<T> = other * this