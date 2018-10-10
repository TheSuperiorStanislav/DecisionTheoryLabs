package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.Lecture1DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class CacheDataLecture1 (private val lecture1Repository: Lecture1DataSource): UseCase<CacheDataLecture1.RequestValues, CacheDataLecture1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            val function = requestValues.function
            val pointList = requestValues.pointList
            val value = requestValues.value

            lecture1Repository.cacheData(function,
                    pointList,
                    value, object : Lecture1DataSource.CacheDataCallback {
                override fun onSaved() {
                    val responseValue = ResponseValue()
                    useCaseCallback?.onSuccess(responseValue)
                }
            })
        }
    }


    class RequestValues(val function: String,
                        val pointList: MutableList<Point>,
                        val value: Double) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue
}