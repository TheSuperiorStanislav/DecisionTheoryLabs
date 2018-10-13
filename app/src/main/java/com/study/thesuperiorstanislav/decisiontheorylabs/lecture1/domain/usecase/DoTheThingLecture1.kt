package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Function


class DoTheThingLecture1: UseCase<DoTheThingLecture1.RequestValues, DoTheThingLecture1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            try {
                val function = requestValues.function
                val u = requestValues.u
                var alpha = requestValues.alpha
                val value = requestValues.value

                val fU = Function.calculateFunc(u, function)
                val uNew = u + alpha * (value - fU)
                val fUNew = Function.calculateFunc(uNew, function)

//                if (Math.abs(value - fU) < Math.abs(value - fUNew))
//                    alpha = -alpha

                val responseValue = ResponseValue(alpha,uNew,fUNew)
                useCaseCallback?.onSuccess(responseValue)
            } catch (e: Exception) {
                useCaseCallback?.onError(UseCase.Error(Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }
    }

    class RequestValues(val function: String,
                        val u: Double,
                        val alpha: Double,
                        val value: Double) : UseCase.RequestValues

    class ResponseValue(val alpha: Double,
                        val uNew: Double,
                        val fUNew:Double) : UseCase.ResponseValue
}