package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

object Lecture1Repository: Lecture1DataSource {
    private var cacheFunction: String? = null
    private var cacheAlpha: Double? = null
    private var cachePointList: MutableList<Point>? = null
    private var cacheValue: Double? = null

    override fun getData(callback: Lecture1DataSource.LoadDataCallback) {
        if (cacheFunction == null
                || cacheAlpha == null
                || cachePointList == null
                || cacheValue == null)
            callback.onDataNotAvailable(UseCase.Error(UseCase.Error.LOAD_ERROR, "No Data"))
        else
            callback.onDataLoaded(cacheFunction!!, cachePointList!!, cacheAlpha!!, cacheValue!!)

    }

    override fun changeAlpha(value: Double, callback: Lecture1DataSource.ChangeAlphaCallback) {
        if (cacheAlpha != null) {
            cacheAlpha = cacheAlpha!! + value
            callback.onSaved()
        } else
            callback.onError(UseCase.Error(UseCase.Error.LOAD_ERROR, "Algorithm isn't running"))
    }


    override fun cacheData(function: String, pointList: MutableList<Point>, alpha: Double, value: Double, callback: Lecture1DataSource.CacheDataCallback) {
        cacheFunction = function
        cacheAlpha = alpha
        cacheValue = value
        cachePointList = pointList
        callback.onSaved()
    }

    override fun cacheData(function: String, pointList: MutableList<Point>, value: Double, callback: Lecture1DataSource.CacheDataCallback) {
        cacheFunction = function
        cacheValue = value
        cachePointList = pointList
        callback.onSaved()
    }
}