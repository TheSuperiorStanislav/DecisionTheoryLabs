package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Regulator

object Repository: DataSource {

    private var cachedPointsListLab1: List<Point>? = null
    private var cachedPointsListLab2: List<Point>? = null
    private var cachedPointsListLab3: List<PointMD>? = null
    private var cachedRegulatorReturnData: Regulator.RegulatorReturnData? = null

    override fun getPointsLab1(callback: DataSource.LoadPointCallback) {
        if (cachedPointsListLab1 != null){
            callback.onPointLoaded(cachedPointsListLab1!!)
        }else{
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,""))
        }
    }

    override fun cachePointsLab1(pointList: List<Point>, callback: DataSource.SavePointCallback) {
        cachedPointsListLab1 = pointList
        callback.onSaved()
    }

    override fun getPointsLab2(callback: DataSource.LoadPointCallback) {
        if (cachedPointsListLab2 != null){
            callback.onPointLoaded(cachedPointsListLab2!!)
        }else{
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,""))
        }
    }

    override fun cachePointsLab2(pointList: List<Point>, callback: DataSource.SavePointCallback) {
        cachedPointsListLab2 = pointList
        callback.onSaved()
    }

    override fun getPointsLab3(callback: DataSource.LoadPointMDCallback) {
        if (cachedPointsListLab3 != null){
            callback.onPointMDLoaded(cachedPointsListLab3!!)
        }else{
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,""))
        }
    }

    override fun cachePointsLab3(pointList: List<PointMD>, callback: DataSource.SavePointMDCallback) {
        cachedPointsListLab3 = pointList
        callback.onSaved()
    }

    override fun getPointsLab5(callback: DataSource.LoadRegulatorReturnDataCallback) {
        if (cachedRegulatorReturnData != null){
            callback.onRegulatorReturnDataLoaded(cachedRegulatorReturnData!!)
        }else{
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,""))
        }
    }

    override fun cachePointsLab5(regulatorReturnData: Regulator.RegulatorReturnData, callback: DataSource.SaveRegulatorReturnDataCallback) {
        cachedRegulatorReturnData = regulatorReturnData
        callback.onSaved()
    }
}