package com.study.thesuperiorstanislav.decisiontheorylabs.lab1

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.DoTheThing
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.GetData
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.CacheDataFromFile
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point


class Lab1Presenter(val lab1View: Lab1Contract.View,
                    private val doTheThing: DoTheThing,
                    private val getData: GetData,
                    private val cacheDataFromFile: CacheDataFromFile): Lab1Contract.Presenter {

    override fun start() {
        lab1View.isActive = true
        getPoints()
    }

    override fun doTheThing(pointList: List<Point>) {
        val requestValue = DoTheThing.RequestValues(pointList)
        UseCaseHandler.execute(doTheThing, requestValue,
                object : UseCase.UseCaseCallback<DoTheThing.ResponseValue> {
                    override fun onSuccess(response: DoTheThing.ResponseValue) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }

                        lab1View.drawGraph(response.pointListOriginal,
                                response.pointListRestored,
                                response.abc)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }

                        lab1View.onError(error)
                    }
                })

    }

    override fun getPoints() {
        val requestValue = GetData.RequestValues()
        UseCaseHandler.execute(getData, requestValue,
                object : UseCase.UseCaseCallback<GetData.ResponseValue> {
                    override fun onSuccess(response: GetData.ResponseValue) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }

                        doTheThing(response.pointList)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }

                        lab1View.onLoadingError(error)
                    }
                })

    }

    override fun savePoint(pointList: List<Point>) {

        val requestValue = CacheDataFromFile.RequestValues(pointList)
        UseCaseHandler.execute(cacheDataFromFile, requestValue,
                object : UseCase.UseCaseCallback<CacheDataFromFile.ResponseValue> {
                    override fun onSuccess(response: CacheDataFromFile.ResponseValue) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab1View may not be able to handle UI updates anymore
                        if (!lab1View.isActive) {
                            return
                        }

                        lab1View.onError(error)
                    }
                })

    }
}
