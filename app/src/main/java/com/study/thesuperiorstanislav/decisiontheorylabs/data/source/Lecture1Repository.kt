package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

object Lecture1Repository: Lecture1DataSource {

    var cacheFunction: String? = null
    var cacheAlpha: Double? = null
    var cachePointList: MutableList<Point>? = null
    var cacheValue: Double? = null

    override fun getData(callback: Lecture1DataSource.LoadDataCallback) {
        if (cacheFunction == null
                || cacheAlpha == null
                || cachePointList == null
                || cacheValue == null)
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.LOAD_ERROR,"No Data"))

        callback.onDataLoaded(cacheFunction!!, cachePointList!!, cacheAlpha!!, cacheValue!!)
    }

    override fun cacheData(function: String, alpha: Double, value: Double, callback: Lecture1DataSource.CacheDataCallback) {
        cacheFunction = function
        cacheAlpha = alpha
        cacheValue =value
        callback.onSaved()
    }

    override fun cacheData(function: String, pointList: MutableList<Point>, alpha: Double, value: Double, callback: Lecture1DataSource.CacheDataCallback) {
        cacheFunction = function
        cacheAlpha = alpha
        cacheValue =value
        cachePointList = pointList
        callback.onSaved()
    }
}