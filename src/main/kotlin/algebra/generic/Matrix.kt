package algebra.generic

data class Matrix<T>(val plus: (T, T) -> T, val times: (T, T) -> T, private val vs: List<Vector<T>>) {
    val numRows = vs.size
    val numColumns = vs.first().length

    init {
        if (vs.isEmpty()) {
            throw IllegalArgumentException()
        }
        if (vs.any { it.length != vs.first().length }) {
            throw IllegalArgumentException()
        }
    }

    operator fun get(i: Int) = getRow(i)

    operator fun get(
        i: Int,
        j: Int,
    ): T {
        if (i !in 0..<numRows || i !in 0..<numColumns) throw IndexOutOfBoundsException()
        return vs[i][j]
    }

    fun getRow(i: Int): Vector<T> {
        if (i !in 0..<numRows) throw IndexOutOfBoundsException()
        return vs[i]
    }

    fun getColumn(i: Int): Vector<T> {
        if (i !in 0..<numColumns) throw IndexOutOfBoundsException()
        return Vector(plus, times, vs.map { it[i] })
    }

    operator fun plus(other: Matrix<T>): Matrix<T> {
        if (this.numRows != other.numRows || this.numColumns != other.numColumns) throw UnsupportedOperationException()
        return this.copy(vs = vs.mapIndexed { index, v -> v + other.getRow(index) })
    }

    operator fun times(other: Matrix<T>): Matrix<T> {
        if (this.numColumns != other.numRows) throw UnsupportedOperationException()
        return this.copy(
            vs =
                vs.map { v ->
                    Vector(plus, times, (0..<other.numColumns).map { i2 -> v dot other.getColumn(i2) })
                },
        )
    }

    operator fun times(scalar: T): Matrix<T> = this.copy(vs = (vs.map { v -> v.times(scalar) }))

    override fun toString(): String {
        val builder = StringBuilder()

        val columnWidths = IntArray(vs[0].length) { 0 }
        for (row in vs) {
            for (i in 0 until row.length) {
                columnWidths[i] = maxOf(columnWidths[i], row[i].toString().length)
            }
        }

        for (row in vs) {
            builder.append("[ ")
            for (i in 0 until row.length) {
                builder.append(row[i].toString().padStart(columnWidths[i], ' ') + ' ')
            }
            builder.append("]\n")
        }

        return builder.toString().trimEnd()
    }

    operator fun T.times(other: Matrix<T>): Matrix<T> = other * this
}
