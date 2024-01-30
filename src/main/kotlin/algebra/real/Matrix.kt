package algebra.real

data class Matrix(private val vs: List<Vector>) {
    val numRows = vs.size
    val numColumns = vs.first().length
    init {
        if (vs.isEmpty()) {
            throw IllegalArgumentException()
        }
        if (vs.any{it.length != vs.first().length}) {
            throw IllegalArgumentException()
        }
    }

    operator fun get(i: Int) = getRow(i)

    operator fun get(i: Int, j: Int): Double {
        if (i !in 0..<numRows || i !in 0..<numColumns) return throw IndexOutOfBoundsException()
        return vs[i][j]
    }

    fun getRow(i: Int): Vector {
        if (i !in 0..<numRows) return throw IndexOutOfBoundsException()
        return vs[i]
    }

    fun getColumn(i: Int): Vector {
        if (i !in 0..<numColumns) return throw IndexOutOfBoundsException()
        return Vector(vs.map{it[i]})
    }


    operator fun plus(other: Matrix): Matrix {
        if (this.numRows != other.numRows || this.numColumns != other.numColumns) return throw UnsupportedOperationException()
        return Matrix(vs.mapIndexed{ index, v -> v + other.getRow(index)})
    }

    operator fun times(other: Matrix): Matrix {
        if (this.numColumns != other.numRows) return throw UnsupportedOperationException()
        return Matrix(vs.map{ v ->
            Vector((0..<other.numColumns).map { i2 -> v dot other.getColumn(i2) })})
    }

    operator fun times(scalar: Double): Matrix = Matrix(vs.map{ v -> v.times(scalar)})

    override fun toString(): String {
        val builder = StringBuilder()

        // Find the maximum width of each column
        val columnWidths = IntArray(vs[0].length) { 0 }
        for (row in vs) {
            for (i in 0 until row.length) {  // Use row.length instead of row.indices
                columnWidths[i] = maxOf(columnWidths[i], row[i].toString().length)
            }
        }

        // Iterate through each row and column, formatting elements with appropriate widths
        for (row in vs) {
            builder.append("[ ")
            for (i in 0 until row.length) {  // Use row.length instead of row.indices
                builder.append("%${columnWidths[i]}.1f ".format(row[i]))  // Use column width for formatting
            }
            builder.append("]\n")
        }

        return builder.toString()
    }
}

operator fun Double.times(other: Matrix): Matrix = other * this

fun main() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )

        val m2 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )

        println(m1 + m2)
}