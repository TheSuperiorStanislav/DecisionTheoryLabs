package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.Lecture1DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class GetDataLecture1 (private val lecture1Repository: Lecture1DataSource): UseCase<GetDataLecture1.RequestValues, GetDataLecture1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            lecture1Repository.getData(object : Lecture1DataSource.LoadDataCallback {
                override fun onDataLoaded(function: String, pointList: MutableList<Point>, alpha: Double, value: Double) {
                    val responseValue = ResponseValue(function, pointList, alpha, value)
                    useCaseCallback?.onSuccess(responseValue)
                }

                override fun onDataNotAvailable(error: Error) {
                    useCaseCallback?.onError(error)
                }
            })
        }
    }


    class RequestValues : UseCase.RequestValues

    class ResponseValue(val function: String,
                        val pointList: MutableList<Point>,
                        val alpha : Double,
                        val value: Double) : UseCase.ResponseValue
}