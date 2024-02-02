package algebra.real

data class Vector(private val v: List<Double>) {
    val length = v.size

    init {
        if (v.isEmpty()) {
            throw IllegalArgumentException()
        }
    }

    operator fun get(i: Int): Double {
        if (i < 0 || i >= length) throw IndexOutOfBoundsException()
        return v[i]
    }

    operator fun plus(other: Vector): Vector {
        if (this.length != other.length) throw UnsupportedOperationException()
        return Vector(this.v.mapIndexed { i, x -> x + other[i] })
    }

    operator fun times(scalar: Double): Vector = Vector(this.v.map { it * scalar })

    infix fun dot(other: Vector): Double {
        if (this.length != other.length) throw UnsupportedOperationException()
        return this.v.foldIndexed(0.0) { index, acc, x -> acc + x * other[index] }
    }

    override fun toString(): String = v.toString().replace("[", "(").replace("]", ")")
}

operator fun Double.times(other: Vector): Vector = other * this
