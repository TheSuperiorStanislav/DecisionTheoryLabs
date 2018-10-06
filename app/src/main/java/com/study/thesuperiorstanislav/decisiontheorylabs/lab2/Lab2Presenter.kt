package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.DoTheThingLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.GetDataLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.CacheDataFromFileLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point


class Lab2Presenter(val lab2View: Lab2Contract.View,
                    private val doTheThingLab2: DoTheThingLab2,
                    private val getDataLab2: GetDataLab2,
                    private val cacheDataFromFileLab1: CacheDataFromFileLab2): Lab2Contract.Presenter {

    override fun start() {
        lab2View.isActive = true
        getPoints()
    }

    override fun getPoints() {
        val requestValue = GetDataLab2.RequestValues()
        UseCaseHandler.execute(getDataLab2, requestValue,
                object : UseCase.UseCaseCallback<GetDataLab2.ResponseValue> {
                    override fun onSuccess(response: GetDataLab2.ResponseValue) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }

                        doTheThing(response.pointList)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }

                        lab2View.onLoadingError(error)
                    }
                })

    }

    override fun doTheThing(pointList: List<Point>) {
        val requestValue = DoTheThingLab2.RequestValues(pointList)
        UseCaseHandler.execute(doTheThingLab2, requestValue,
                object : UseCase.UseCaseCallback<DoTheThingLab2.ResponseValue> {
                    override fun onSuccess(response: DoTheThingLab2.ResponseValue) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }

                        lab2View.showGraphs(response.pointListOriginal,
                                response.pointListRestored,
                                response.pointListCs)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }

                        lab2View.onError(error)
                    }
                })

    }

    override fun savePoint(pointList: List<Point>) {

        val requestValue = CacheDataFromFileLab2.RequestValues(pointList)
        UseCaseHandler.execute(cacheDataFromFileLab1, requestValue,
                object : UseCase.UseCaseCallback<CacheDataFromFileLab2.ResponseValue> {
                    override fun onSuccess(response: CacheDataFromFileLab2.ResponseValue) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab2View may not be able to handle UI updates anymore
                        if (!lab2View.isActive) {
                            return
                        }

                        lab2View.onError(error)
                    }
                })

    }
}
