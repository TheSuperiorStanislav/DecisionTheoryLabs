package com.study.thesuperiorstanislav.decisiontheorylabs.lab5.domain

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Regulator

class DoTheThingLab5(private val dataSource: DataSource): UseCase<DoTheThingLab5.RequestValues, DoTheThingLab5.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            val n = requestValues.n
            val delta = requestValues.delta
            val function = requestValues.function
            val alpha = requestValues.alpha
            val regulatorReturnData = Regulator.doTheThing(n, delta, function, alpha)
            dataSource.cacheDataLab5(regulatorReturnData, object : DataSource.SaveRegulatorReturnDataCallback{
                override fun onSaved() {
                    val responseValue = ResponseValue(regulatorReturnData.pointListOriginal,regulatorReturnData.pointListRestored)
                    useCaseCallback?.onSuccess(responseValue)
                }
            })
        }
    }

    class RequestValues(val n: Int, val delta: Double, val function: String, val alpha:Double) : UseCase.RequestValues

    class ResponseValue(val pointListOriginal: List<Point>,
                        val pointListRestored: List<Point>) : UseCase.ResponseValue
}