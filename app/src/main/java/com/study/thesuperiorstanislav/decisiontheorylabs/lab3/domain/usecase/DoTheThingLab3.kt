package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.RegressionMD

class DoTheThingLab3: UseCase<DoTheThingLab3.RequestValues, DoTheThingLab3.ResponseValue>() {

    @Suppress("UNCHECKED_CAST")
    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            //try {
            val pointListOriginal = requestValues.pointListOriginal

            val calculatedData = RegressionMD.doTheThing(pointListOriginal)
            val pointListRestored = calculatedData[0] as List<PointMD>
            val cs = calculatedData[1] as Array<Double>

            val pointListOriginalToReturn = mutableListOf<Point>()
            val pointListRestoredToReturn = mutableListOf<Point>()
            pointListOriginal.forEachIndexed { index, pointMD ->
                pointListOriginalToReturn.add(index, Point(pointMD.u.first(),pointMD.x))
                pointListRestoredToReturn.add(index,
                        Point(pointListRestored[index].u.first(),pointListRestored[index].x))
            }
            val responseValue = ResponseValue(pointListOriginalToReturn,pointListRestoredToReturn, cs)
            useCaseCallback?.onSuccess(responseValue)
//            }catch (e:Exception){
//                useCaseCallback?.onError(UseCase.Error(Error.UNKNOWN_ERROR, e.localizedMessage))
//            }
        }
    }

    class RequestValues(val pointListOriginal: List<PointMD>) : UseCase.RequestValues

    class ResponseValue(val pointListOriginal: List<Point>,
                        val pointListRestored: List<Point>,
                        val cs: Array<Double> ) : UseCase.ResponseValue
}