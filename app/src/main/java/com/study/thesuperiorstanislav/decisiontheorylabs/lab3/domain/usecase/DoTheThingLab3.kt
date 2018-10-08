package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.RegressionMD

class DoTheThingLab3: UseCase<DoTheThingLab3.RequestValues, DoTheThingLab3.ResponseValue>() {

    @Suppress("UNCHECKED_CAST")
    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            try {
                val pointListOriginal = requestValues.pointListOriginal
                val calculatedData = RegressionMD.doTheThing(pointListOriginal)
                val pointListRestored = calculatedData[0] as List<PointMD>
                val pointListCs = calculatedData[1] as List<Point>
                val responseValue = ResponseValue(pointListRestored, pointListCs)
                useCaseCallback?.onSuccess(responseValue)
            }catch (e:Exception){
                useCaseCallback?.onError(UseCase.Error(Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }
    }

    class RequestValues(val pointListOriginal: List<PointMD>) : UseCase.RequestValues

    class ResponseValue(val pointListRestored: List<PointMD>,
                        val pointListCs: List<Point>) : UseCase.ResponseValue
}