package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

object Repository: DataSource {

    var cachedPointsListLab1: List<Point>? = null
    var cachedPointsListLab2: List<Point>? = null

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
}