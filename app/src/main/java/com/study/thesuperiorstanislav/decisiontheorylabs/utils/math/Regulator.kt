package com.study.thesuperiorstanislav.decisiontheorylabs.utils.math

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

object Regulator {

    fun doTheThing(n: Int, delta: Double, function: String,alpha:Double): RegulatorReturnData {
        val staringDot = 0 - n / 2.0 * delta
        val pointListOriginal: List<Point> = calculatePointOriginal(n, staringDot, delta, function)
        val pointListRestored = List(n) { index ->
            findModelPoint(staringDot, pointListOriginal[index].y, function, alpha)
        }
        return RegulatorReturnData(pointListOriginal,pointListRestored)
    }

    private fun findModelPoint(staringDot: Double, x:Double, function: String, alpha: Double): Point {
        var curU = staringDot
        var curX: Double
        var count = 0
        do {
            curX = Function.calculateFunc(curU, function)
            curU += alpha * (x - curX)
            curX = Function.calculateFunc(curU, function)
            count++
        }while (Math.abs(curX - x) > 0.00001
                && curU != Double.NaN
                && curU != Double.POSITIVE_INFINITY
                && curU != Double.NEGATIVE_INFINITY
                && count < 1000)

        return Point(curU,curX)
    }

    private fun calculatePointOriginal(n: Int, staringDot: Double, delta: Double, function: String): MutableList<Point> {
        val pointList = mutableListOf<Point>()
        for (i in 0 until n) {
            val u = staringDot + i.toDouble() * delta
            pointList.add(Point(u, Function.calculateFunc(u, function)))
        }
        return pointList
    }

//    private fun centralLimitTheorem(expectedValue: Double, variance: Double, randomForInterference: Random): Double =
//            Math.sqrt(variance) * Math.sqrt(12.0 / 100) * calcForCLT(randomForInterference) + expectedValue
//
//    private fun calcForCLT(randomForInterference: Random): Double {
//        var result = 0.0
//        for (i in 0..99) {
//            result += randomForInterference.nextDouble() - 0.5
//        }
//        return result
//    }

    data class RegulatorReturnData(val pointListOriginal: List<Point>,
                                   val pointListRestored: List<Point>)
}