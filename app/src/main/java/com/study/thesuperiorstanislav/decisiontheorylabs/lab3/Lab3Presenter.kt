package com.study.thesuperiorstanislav.decisiontheorylabs.lab3

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.CacheDataFromFileLab3
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.DoTheThingLab3
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.GetDataLab3

class Lab3Presenter (val lab3View: Lab3Contract.View,
                     private val doTheThingLab3: DoTheThingLab3,
                     private val getDataLab3: GetDataLab3,
                     private val cacheDataFromFileLab3: CacheDataFromFileLab3): Lab3Contract.Presenter {

    override fun start() {
        lab3View.isActive = true
        getPoints()
    }

    override fun getPoints() {
        val requestValue = GetDataLab3.RequestValues()
        UseCaseHandler.execute(getDataLab3, requestValue,
                object : UseCase.UseCaseCallback<GetDataLab3.ResponseValue> {
                    override fun onSuccess(response: GetDataLab3.ResponseValue) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }

                        doTheThing(response.pointList)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }

                        lab3View.onLoadingError(error)
                    }
                })

    }

    override fun doTheThing(pointList: List<PointMD>) {
        val requestValue = DoTheThingLab3.RequestValues(pointList)
        UseCaseHandler.execute(doTheThingLab3, requestValue,
                object : UseCase.UseCaseCallback<DoTheThingLab3.ResponseValue> {
                    override fun onSuccess(response: DoTheThingLab3.ResponseValue) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }

                        lab3View.showData(response.pointListRestored,
                                response.pointListCs)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }

                        lab3View.onError(error)
                    }
                })

    }

    override fun savePoint(pointList: List<PointMD>) {

        val requestValue = CacheDataFromFileLab3.RequestValues(pointList)
        UseCaseHandler.execute(cacheDataFromFileLab3, requestValue,
                object : UseCase.UseCaseCallback<CacheDataFromFileLab3.ResponseValue> {
                    override fun onSuccess(response: CacheDataFromFileLab3.ResponseValue) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab3View may not be able to handle UI updates anymore
                        if (!lab3View.isActive) {
                            return
                        }

                        lab3View.onError(error)
                    }
                })

    }
}