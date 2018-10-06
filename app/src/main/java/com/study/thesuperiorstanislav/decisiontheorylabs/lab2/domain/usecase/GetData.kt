package com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class GetData(private val repository: DataSource): UseCase<GetData.RequestValues, GetData.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            repository.getPointsLab2(object : DataSource.LoadPointCallback {
                override fun onPointLoaded(pointList: List<Point>) {
                    val responseValue = ResponseValue(pointList)
                    useCaseCallback?.onSuccess(responseValue)
                }


                override fun onDataNotAvailable(error: Error) {
                    useCaseCallback?.onError(error)
                }
            })
        }
    }


    class RequestValues : UseCase.RequestValues

    class ResponseValue(val pointList: List<Point>) : UseCase.ResponseValue
}
