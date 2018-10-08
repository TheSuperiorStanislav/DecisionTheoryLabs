package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.DataSource
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

class CacheDataFromFileLab3(private val repository: DataSource): UseCase<CacheDataFromFileLab3.RequestValues, CacheDataFromFileLab3.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            val pointList = requestValues.pointList
            repository.cachePointsLab3(pointList,object : DataSource.SavePointMDCallback {
                override fun onSaved() {
                    val responseValue = ResponseValue()
                    useCaseCallback?.onSuccess(responseValue)
                }
            })
        }
    }


    class RequestValues(val pointList: List<PointMD>) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue
}