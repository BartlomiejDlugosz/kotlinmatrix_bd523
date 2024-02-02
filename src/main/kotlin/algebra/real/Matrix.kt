package algebra.real

data class Matrix(private val vs: List<Vector>) {
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
    ): Double {
        if (i !in 0..<numRows || i !in 0..<numColumns) throw IndexOutOfBoundsException()
        return vs[i][j]
    }

    fun getRow(i: Int): Vector {
        if (i !in 0..<numRows) throw IndexOutOfBoundsException()
        return vs[i]
    }

    fun getColumn(i: Int): Vector {
        if (i !in 0..<numColumns) throw IndexOutOfBoundsException()
        return Vector(vs.map { it[i] })
    }

    operator fun plus(other: Matrix): Matrix {
        if (this.numRows != other.numRows || this.numColumns != other.numColumns) throw UnsupportedOperationException()
        return Matrix(vs.mapIndexed { index, v -> v + other.getRow(index) })
    }

    operator fun times(other: Matrix): Matrix {
        if (this.numColumns != other.numRows) throw UnsupportedOperationException()
        return Matrix(
            vs.map { v ->
                Vector((0..<other.numColumns).map { i2 -> v dot other.getColumn(i2) })
            },
        )
    }

    operator fun times(scalar: Double): Matrix = Matrix(vs.map { v -> v.times(scalar) })

    override fun toString(): String {
        val builder = StringBuilder()

        val columnWidths = IntArray(vs[0].length) { 0 }
        for (row in vs) {
            for (i in 0 until row.length) { // Use row.length instead of row.indices
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
}

operator fun Double.times(other: Matrix): Matrix = other * this
