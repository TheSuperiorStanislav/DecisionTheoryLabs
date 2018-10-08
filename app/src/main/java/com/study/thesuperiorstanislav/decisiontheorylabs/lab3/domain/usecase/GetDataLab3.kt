package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

class GetDataLab3 (private val repository: DataSource): UseCase<GetDataLab3.RequestValues, GetDataLab3.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            repository.getPointsLab3(object : DataSource.LoadPointMDCallback {
                override fun onPointMDLoaded(pointList: List<PointMD>) {
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

    class ResponseValue(val pointList: List<PointMD>) : UseCase.ResponseValue
}