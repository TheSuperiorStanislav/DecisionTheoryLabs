package com.study.thesuperiorstanislav.decisiontheorylabs.utils.Math

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

object RegressionMD {

    private const val coreType: Int = 1

    private var pointListOriginal: List<PointMD> = arrayListOf()
    private var pointListRestored: MutableList<PointMD> = mutableListOf()
    private var pointListCs: MutableList<Point> = mutableListOf()

    fun doTheThing(pointListOriginal: List<PointMD>):Array<List<Any>>{
        RegressionMD.pointListOriginal = pointListOriginal
        pointListRestored.clear()
        pointListCs.clear()

        val cs = findOptimal()
        calculateDotsModel(cs)
        return arrayOf(
                pointListRestored,
                pointListCs)
    }

    private fun calculateDotsModel(cs: Double) {
        pointListOriginal.forEach {
            pointListRestored.add(PointMD(it.u, calculateRegression(cs, it.u)))
        }
    }


    private fun calculateRegression(cs: Double, u: Array<Double>): Double {
        var temp1 = 0.0
        var temp2 = 0.0
        pointListOriginal.forEach { pointMD ->
            var temp3 = 1.0
            u.forEach { uI ->
                pointMD.u.forEach {
                    temp3 *= cores(calculateCore(cs, uI, it))
                }
            }
            temp1 += temp3
            temp2 += pointMD.x * temp3
        }
        return temp2 / temp1
    }

    private fun calculateCore(cs: Double, u: Double, uI: Double): Double {
        return (u - uI) / cs
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
            val yCalculated = calculateRegression(cs, it.u)
            w += Math.pow(it.x - yCalculated, 2.0)
        }
        return w
    }

}