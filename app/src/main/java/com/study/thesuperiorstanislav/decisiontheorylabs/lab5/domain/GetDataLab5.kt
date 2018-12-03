package com.study.thesuperiorstanislav.decisiontheorylabs.lab5.domain

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Regulator

class GetDataLab5(private val dataSource: DataSource): UseCase<GetDataLab5.RequestValues, GetDataLab5.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            dataSource.getDataLab5(object : DataSource.LoadRegulatorReturnDataCallback {
                override fun onRegulatorReturnDataLoaded(regulatorReturnData: Regulator.RegulatorReturnData) {
                    val responseValue = ResponseValue(regulatorReturnData.pointListOriginal,
                            regulatorReturnData.pointListRestored)
                    useCaseCallback?.onSuccess(responseValue)
                }

                override fun onDataNotAvailable(error: Error) {
                    useCaseCallback?.onError(error)
                }
            })
        }
    }


    class RequestValues : UseCase.RequestValues

    class ResponseValue(val pointListOriginal: List<Point>,
                        val pointListRestored: List<Point>) : UseCase.ResponseValue
}