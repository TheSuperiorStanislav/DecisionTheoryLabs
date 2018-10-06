package com.study.thesuperiorstanislav.decisiontheorylabs.utils

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

object GraphHelper {
    fun findMinMax(pointListOriginal: List<Point>, pointListRestored: List<Point>): Array<Double> {
        var maxX = Double.MIN_VALUE
        var maxY = Double.MIN_VALUE
        var minX = Double.MAX_VALUE
        var minY = Double.MAX_VALUE

        for (i in 0 until pointListOriginal.size) {
            if (minX > pointListOriginal[i].x) {
                minX = pointListOriginal[i].x
            }
            if (minX > pointListRestored[i].x) {
                minX = pointListRestored[i].x
            }

            if (minY > pointListOriginal[i].y) {
                minY = pointListOriginal[i].y
            }
            if (minY > pointListRestored[i].y) {
                minY = pointListRestored[i].y
            }

            if (maxX < pointListOriginal[i].x) {
                maxX = pointListOriginal[i].x
            }
            if (maxX < pointListRestored[i].x) {
                maxX = pointListRestored[i].x
            }

            if (maxY < pointListOriginal[i].y) {
                maxY = pointListOriginal[i].y
            }
            if (maxY < pointListRestored[i].y) {
                maxY = pointListRestored[i].y
            }
        }

        return arrayOf(minX, maxX, minY, maxY)
    }

    fun findMinMax(pointList: List<Point>): Array<Double> {
        var maxX = Double.MIN_VALUE
        var maxY = Double.MIN_VALUE
        var minX = Double.MAX_VALUE
        var minY = Double.MAX_VALUE

        for (i in 0 until pointList.size) {
            if (minX > pointList[i].x) {
                minX = pointList[i].x
            }

            if (minY > pointList[i].y) {
                minY = pointList[i].y
            }

            if (maxX < pointList[i].x) {
                maxX = pointList[i].x
            }

            if (maxY < pointList[i].y) {
                maxY = pointList[i].y
            }
        }

        return arrayOf(minX, maxX, minY, maxY)
    }
}