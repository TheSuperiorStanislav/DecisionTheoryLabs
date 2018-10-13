package com.study.thesuperiorstanislav.decisiontheorylabs.utils.math

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point


object Regression {

    private const val coreType: Int = 1

    private var pointListOriginal: List<Point> = arrayListOf()
    private var pointListRestored: MutableList<Point> = mutableListOf()
    private var pointListCs: MutableList<Point> = mutableListOf()

    fun doTheThing(pointListOriginal: List<Point>):Array<List<Point>>{
        Regression.pointListOriginal = pointListOriginal
        pointListRestored.clear()
        pointListCs.clear()

        val cs = findOptimal()
        calculateDotsModel(cs)
        return arrayOf(
                Regression.pointListOriginal,
                pointListRestored,
                pointListCs)
    }

    private fun findOptimal(): Double {
        var csMin = 0.01
        var wMin = java.lang.Double.MAX_VALUE
        var cs = 0.01
        val step = 0.01
        for (i in 0..99) {
            cs = Math.round(cs * 100000.0) / 100000.0
            val tempW = calculateW(cs)
            pointListCs.add(Point(cs, tempW))
            if (tempW < wMin) {
                csMin = cs
                wMin = tempW
            }
            cs += step
        }
        return Math.round(csMin * 100000.0) / 100000.0
    }

    private fun calculateW(cs: Double): Double {
        var w = 0.0
        pointListOriginal.forEach {
            val yCalculated = calculateRegression(cs, it.x)
            w += Math.pow(it.y - yCalculated, 2.0)
        }
        return w
    }

    private fun calculateDotsModel(cs: Double) {
        pointListOriginal.forEach {
            pointListRestored.add(Point(it.x, calculateRegression(cs, it.x)))
        }
    }


    private fun calculateRegression(cs: Double, x: Double): Double {
        var temp1 = 0.0
        var temp2 = 0.0
        pointListOriginal.forEach {
            val core = cores(calculateCore(cs, x, it.x))
            temp1 += core
            temp2 += it.y * core
        }
        return temp2 / temp1
    }

    private fun calculateCore(cs: Double, x: Double, xI: Double): Double {
        return (x - xI) / cs
    }

    private fun cores(z: Double): Double {
        var k = 0.0
        if (Math.abs(z) <= 1) {
            when (coreType) {
                1 -> k = 0.5
                2 -> k = 1.0 - Math.abs(z)
                3 -> k = 0.75 * (1.0 - Math.pow(z, 2.0))
                4 -> k = (1.0 + 2.0 * Math.abs(z)) * Math.pow(1.0 - Math.abs(z), 2.0)
            }
        }
        return k
    }

}