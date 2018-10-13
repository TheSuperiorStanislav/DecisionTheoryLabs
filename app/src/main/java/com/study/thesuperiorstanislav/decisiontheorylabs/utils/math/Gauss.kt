package com.study.thesuperiorstanislav.decisiontheorylabs.utils.math

object Gauss {
    private fun calculateGauss(matrix: Array<DoubleArray>):Array<DoubleArray>{
        for (s in 0..(matrix.size - 2)) {
            for (i in s + 1..(matrix.size - 1)) {
                val multiplier = matrix[i][s]
                for (g in 0..(matrix[i].size - 1)) {
                    matrix[i][g] += -matrix[s][g] * multiplier/ matrix[s][s]
                }
            }
        }
        return matrix
    }

    fun calculateABC(matrix: Array<DoubleArray>):DoubleArray{
        val matrixCalculated = calculateGauss(matrix)

        val c = matrixCalculated[2][3] / matrixCalculated[2][2]
        val b = (matrixCalculated[1][3] - c * matrixCalculated[1][2]) / matrixCalculated[1][1]
        val a = (matrixCalculated[0][3] - c * matrixCalculated[0][2] - b * matrixCalculated[0][1]) / matrixCalculated[0][0]
        return doubleArrayOf(a,b,c)
    }
}