package com.study.thesuperiorstanislav.decisiontheorylabs.lab5

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCaseHandler
import com.study.thesuperiorstanislav.decisiontheorylabs.lab5.domain.DoTheThingLab5
import com.study.thesuperiorstanislav.decisiontheorylabs.lab5.domain.GetDataLab5

class Lab5Presenter(private val lab5View: Lab5Contract.View,
                    private val doTheThingLab5: DoTheThingLab5,
                    private val getDataLab5: GetDataLab5):Lab5Contract.Presenter{

    override fun start() {
        lab5View.isActive = true
        getData()
    }
    
    override fun getData() {
        val requestValue = GetDataLab5.RequestValues()
        UseCaseHandler.execute(getDataLab5, requestValue,
                object : UseCase.UseCaseCallback<GetDataLab5.ResponseValue> {
                    override fun onSuccess(response: GetDataLab5.ResponseValue) {
                        // The lab5View may not be able to handle UI updates anymore
                        if (!lab5View.isActive) {
                            return
                        }

                        lab5View.drawGraph(response.pointListOriginal,
                                response.pointListRestored)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab5View may not be able to handle UI updates anymore
                        if (!lab5View.isActive) {
                            return
                        }

                        lab5View.onError(error)
                    }
                })
    }

    override fun doTheThing(n: Int, delta: Double, function: String, alpha: Double) {
        val requestValue = DoTheThingLab5.RequestValues(n, delta, function, alpha)
        UseCaseHandler.execute(doTheThingLab5, requestValue,
                object : UseCase.UseCaseCallback<DoTheThingLab5.ResponseValue> {
                    override fun onSuccess(response: DoTheThingLab5.ResponseValue) {
                        // The lab5View may not be able to handle UI updates anymore
                        if (!lab5View.isActive) {
                            return
                        }

                        lab5View.drawGraph(response.pointListOriginal,
                                response.pointListRestored)
                    }

                    override fun onError(error: UseCase.Error) {
                        // The lab5View may not be able to handle UI updates anymore
                        if (!lab5View.isActive) {
                            return
                        }

                        lab5View.onError(error)
                    }
                })
    }

}