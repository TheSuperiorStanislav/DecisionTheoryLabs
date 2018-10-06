package com.study.thesuperiorstanislav.decisiontheorylabs.lab1

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.DoTheThingLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.GetDataLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.CacheDataFromFileLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point


class Lab1Presenter(val lab1View: Lab1Contract.View,
                    private val doTheThingLab1: DoTheThingLab1,
                    private val getDataLab1: GetDataLab1,
                    private val cacheDataFromFileLab1: CacheDataFromFileLab1): Lab1Contract.Presenter {

    override fun start() {
        lab1View.isActive = true
        getPoints()
    }

    override fun doTheThing(pointList: List<Point>) {
        val requestValue = DoTheThingLab1.RequestValues(pointList)
        UseCaseHandler.execute(doTheThingLab1, requestValue,
                object : UseCase.UseCaseCallback<DoTheThingLab1.ResponseValue> {
                    override fun onSuccess(response: DoTheThingLab1.ResponseValue) {
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
        val requestValue = GetDataLab1.RequestValues()
        UseCaseHandler.execute(getDataLab1, requestValue,
                object : UseCase.UseCaseCallback<GetDataLab1.ResponseValue> {
                    override fun onSuccess(response: GetDataLab1.ResponseValue) {
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

        val requestValue = CacheDataFromFileLab1.RequestValues(pointList)
        UseCaseHandler.execute(cacheDataFromFileLab1, requestValue,
                object : UseCase.UseCaseCallback<CacheDataFromFileLab1.ResponseValue> {
                    override fun onSuccess(response: CacheDataFromFileLab1.ResponseValue) {
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
