package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase.CacheDataLecture1
import com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase.ChangeAlphaLecture1
import com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase.DoTheThingLecture1
import com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase.GetDataLecture1
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Math.Function

class Lecture1Presenter(private val lecture1View: Lecture1Contract.View,
                        private val getDataLecture1: GetDataLecture1,
                        private val doTheThingLecture1: DoTheThingLecture1,
                        private val cacheDataLecture1: CacheDataLecture1,
                        private val changeAlphaLecture1: ChangeAlphaLecture1):Lecture1Contract.Presenter {

    private var isRunning = false
    private var isPaused = false

    override fun start() {
        lecture1View.isActive = true
        getData()
    }

    override fun setRunStats(isPaused: Boolean, isRunning: Boolean) {
        this.isRunning = isRunning
        this.isPaused = isPaused
    }

    override fun getData() {
        val requestValue = GetDataLecture1.RequestValues()
        UseCaseHandler.execute(getDataLecture1, requestValue,
                object : UseCase.UseCaseCallback<GetDataLecture1.ResponseValue> {
                    override fun onSuccess(response: GetDataLecture1.ResponseValue) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        lecture1View.showData(response.function,response.pointList, response.value, response.alpha)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        lecture1View.onLoadingError(error)
                    }
                })
    }

    override fun startTheThing(function: String, value: Double) {
        isRunning = true
        if (isPaused) {
            isPaused = false
            getData()
        }
        else {
            val pointList = mutableListOf<Point>()
            pointList.add(Point(0.0, Function.calculateFunc(0.0, function)))
            cacheData(function, pointList, value)
        }
    }

    override fun stopTheThing() {
        isRunning = false
        isPaused = true
    }

    override fun changeAlpha(flag: Boolean) {
        val requestValue = ChangeAlphaLecture1.RequestValues(flag)
        UseCaseHandler.execute(changeAlphaLecture1, requestValue,
                object : UseCase.UseCaseCallback<ChangeAlphaLecture1.ResponseValue> {
                    override fun onSuccess(response: ChangeAlphaLecture1.ResponseValue) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        lecture1View.onError(error)
                    }
                })
    }

    override fun doTheThing(function: String, pointList: MutableList<Point>, alpha: Double, value: Double){
        val requestValue = DoTheThingLecture1.RequestValues(function,
                pointList.last().x,
                alpha,
                value)

        UseCaseHandler.execute(doTheThingLecture1, requestValue,
                object : UseCase.UseCaseCallback<DoTheThingLecture1.ResponseValue> {
                    override fun onSuccess(response: DoTheThingLecture1.ResponseValue) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        pointList.add(Point(response.uNew, response.fUNew))

                        if (Math.abs(response.fUNew - value) > 0.01 && response.fUNew != Double.NaN
                            && response.fUNew != Double.POSITIVE_INFINITY && response.fUNew != Double.NEGATIVE_INFINITY)
                            cacheData(function, pointList, value)
                        else{
                            isRunning = false
                            cacheData(function, pointList, value)
                        }

                    }

                    override fun onError(error: UseCase.Error) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        lecture1View.onError(error)
                    }
                })

    }

    private fun cacheData(function: String,
                          pointList: MutableList<Point>,
                          value: Double){
        val requestValue = CacheDataLecture1.RequestValues(function,pointList,value)
        UseCaseHandler.execute(cacheDataLecture1, requestValue,
                object : UseCase.UseCaseCallback<CacheDataLecture1.ResponseValue> {
                    override fun onSuccess(response: CacheDataLecture1.ResponseValue) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        getData()
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lecture1View may not be able to handle UI updates anymore
                        if (!lecture1View.isActive) {
                            return
                        }

                        lecture1View.onError(error)
                    }
                })

    }
}