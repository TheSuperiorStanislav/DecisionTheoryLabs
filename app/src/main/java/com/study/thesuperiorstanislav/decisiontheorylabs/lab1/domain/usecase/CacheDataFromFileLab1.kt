package com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class CacheDataFromFileLab1(private val repository: DataSource): UseCase<CacheDataFromFileLab1.RequestValues, CacheDataFromFileLab1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            val pointList = requestValues.pointList
            repository.cachePointsLab1(pointList,object : DataSource.SavePointCallback {
                override fun onSaved() {
                    val responseValue = ResponseValue()
                    useCaseCallback?.onSuccess(responseValue)
                }
            })
        }
    }


    class RequestValues(val pointList: List<Point>) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue
}