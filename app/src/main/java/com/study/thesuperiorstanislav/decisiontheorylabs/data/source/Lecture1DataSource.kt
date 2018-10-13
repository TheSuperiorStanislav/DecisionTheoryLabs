package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface Lecture1DataSource {
    interface LoadDataCallback {

        fun onDataLoaded(function: String, pointList: MutableList<Point>, alpha: Double, value: Double)

        fun onDataNotAvailable(error: UseCase.Error)
    }

    interface CacheDataCallback {

        fun onSaved()

    }

    interface ChangeAlphaCallback {

        fun onSaved()

        fun onError(error: UseCase.Error)

    }

    fun getData(callback: LoadDataCallback)

    fun changeAlpha(value: Double, callback: ChangeAlphaCallback)

    fun cacheData(function: String, pointList: MutableList<Point>, value: Double, callback: CacheDataCallback)

    fun cacheData(function: String, pointList: MutableList<Point>, alpha: Double, value: Double, callback: CacheDataCallback)
}