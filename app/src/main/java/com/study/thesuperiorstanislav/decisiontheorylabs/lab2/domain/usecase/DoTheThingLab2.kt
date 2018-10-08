package com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Math.Regression

class DoTheThingLab2: UseCase<DoTheThingLab2.RequestValues, DoTheThingLab2.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            try {
                val pointListOriginal = requestValues.pointListOriginal
                val calculatedData = Regression.doTheThing(pointListOriginal)
                val responseValue = ResponseValue(calculatedData[0], calculatedData[1], calculatedData[2])
                useCaseCallback?.onSuccess(responseValue)
            }catch (e:Exception){
                useCaseCallback?.onError(UseCase.Error(Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }
    }

    class RequestValues(val pointListOriginal: List<Point>) : UseCase.RequestValues

    class ResponseValue(val pointListOriginal: List<Point>,
                        val pointListRestored: List<Point>,
                        val pointListCs: List<Point>) : UseCase.ResponseValue
}
