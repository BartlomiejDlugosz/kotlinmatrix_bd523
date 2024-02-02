package algebra.generic

class AlgebraFactory<T>(val plus: (T, T) -> T, val times: (T, T) -> T) {
    fun makeVector(xs: List<T>): Vector<T> = Vector(plus, times, xs)

    fun makeMatrix(xs: List<List<T>>): Matrix<T> = Matrix(plus, times, xs.map { this.makeVector(it) })
}
