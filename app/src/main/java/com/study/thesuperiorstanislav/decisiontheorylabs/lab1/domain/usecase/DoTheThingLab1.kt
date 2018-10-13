package com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Gauss

class DoTheThingLab1(): UseCase<DoTheThingLab1.RequestValues, DoTheThingLab1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            try {
                val pointListOriginal = requestValues.pointListOriginal

                val matrix = createMatrix(pointListOriginal)

                val abc = Gauss.calculateABC(matrix)

                val pointsRestored = calculatePointsRestored(abc, pointListOriginal)

                val responseValue = ResponseValue(pointListOriginal, pointsRestored, abc)

                useCaseCallback?.onSuccess(responseValue)
            } catch (e: Exception) {
                useCaseCallback?.onError(UseCase.Error(Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }
    }

    private fun createMatrix(pointListOriginal: List<Point>): Array<DoubleArray>{
        var sumY = 0.0
        var sumYX = 0.0
        var sumYX2 = 0.0

        var sumX = 0.0
        var sumX2 = 0.0
        var sumX3 = 0.0
        var sumX4 = 0.0

        pointListOriginal.forEach {
            sumY += it.y
            sumYX += it.y * it.x
            sumYX2 += it.y * Math.pow(it.x , 2.0)
            sumX += it.x
            sumX2 += Math.pow(it.x , 2.0)
            sumX3 += Math.pow(it.x , 3.0)
            sumX4 += Math.pow(it.x , 4.0)
        }
        return arrayOf(
                doubleArrayOf(sumX2, sumX, pointListOriginal.size.toDouble(), sumY),
                doubleArrayOf(sumX3, sumX2, sumX, sumYX),
                doubleArrayOf(sumX4, sumX3, sumX2,sumYX2))
    }

    private fun calculatePointsRestored(abc:DoubleArray, pointListOriginal: List<Point>): List<Point>{
        val pointListRestored = arrayListOf<Point>()
        pointListOriginal.forEach {
            pointListRestored.add(Point(it.x,func(abc,it.x)))
        }
        return pointListRestored
    }

    private fun func(abc:DoubleArray,x:Double): Double{
        return abc[0]*Math.pow(x,2.0) + abc[1]*x + abc[2]
    }

    class RequestValues(val pointListOriginal: List<Point>) : UseCase.RequestValues

    class ResponseValue(val pointListOriginal: List<Point>,
                        val pointListRestored: List<Point>,
                        val abc:DoubleArray) : UseCase.ResponseValue
}
