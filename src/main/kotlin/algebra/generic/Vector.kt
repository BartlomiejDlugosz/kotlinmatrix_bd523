package algebra.generic

data class Vector<T>(val plus: (T, T) -> T, val times: (T, T) -> T, private val v: List<T>) {
    val length = v.size

    init {
        if (v.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    operator fun get(i: Int): T {
        if (i < 0 || i >= length) throw IndexOutOfBoundsException()
        return v[i]
    }

    operator fun plus(other: Vector<T>): Vector<T> {
        if (this.length != other.length) throw UnsupportedOperationException()
        return this.copy(v = this.v.mapIndexed { i, x -> plus(x, other[i]) })
    }

    operator fun times(scalar: T): Vector<T> {
        return this.copy(v = this.v.map { times(it, scalar) })
    }

    infix fun dot(other: Vector<T>): T {
        if (this.length != other.length) throw UnsupportedOperationException()

        return this.v.zip(other.v).map { (x, y) -> times(x, y) }.reduce { acc, x -> plus(acc, x) }
    }

    override fun toString(): String = v.toString().replace("[", "(").replace("]", ")")

    operator fun T.times(other: Vector<T>): Vector<T> = other * this
}
