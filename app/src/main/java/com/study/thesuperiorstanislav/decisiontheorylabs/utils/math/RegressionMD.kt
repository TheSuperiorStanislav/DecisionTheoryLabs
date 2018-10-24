package com.study.thesuperiorstanislav.decisiontheorylabs.utils.math

import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

object RegressionMD {

    private const val coreType: Int = 1

    private var pointListOriginal: List<PointMD> = arrayListOf()
    private var pointListRestored: MutableList<PointMD> = mutableListOf()

    fun doTheThing(pointListOriginal: List<PointMD>):Array<Any>{
        RegressionMD.pointListOriginal = pointListOriginal
        pointListRestored.clear()

        val cs = findOptimal()
        calculateDotsModel(cs)
        return arrayOf(
                pointListRestored,
                cs)
    }

    private fun calculateDotsModel(cs: Array<Double>) {
        pointListOriginal.forEach {
            val uRestored = Array(it.u.size) { i -> it.u[i] + 0.5}
            pointListRestored.add(PointMD(uRestored, calculateRegression(cs, uRestored)))
        }
    }


    private fun calculateRegression(cs: Array<Double>, u: Array<Double>): Double {
        var temp1 = 0.0
        var temp2 = 0.0
        pointListOriginal.forEach { pointMD ->
            var temp3 = 1.0
            u.forEach { uI ->
                pointMD.u.forEachIndexed { index, u ->
                    temp3 *= cores(calculateCore(cs[index], uI, u))
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

    private fun findOptimal(): Array<Double> {
        val n = pointListOriginal.first().u.size

        val cs = Array(n) { _ -> 2.0 }
        val csNew = Array(n) { _ -> 0.0 }
        val grad = Array(n) { _ -> 0.0 }
        val dirs = Array(n) { indexRow ->
            Array(n) { indexColumn ->
                if (indexRow == indexColumn)
                    1.0
                else
                    0.0
            }
        }

        var t = 1.0
        val tStatic = 1.0

        val stillSearching = true
        var isPrevOk = false
        var isStep9: Boolean

        var step = 0
        val limit = n * 4

        val e1 = 0.2
        val e2 = 0.2

        do {
            if (step >= limit)
                return cs
            else {
                var k = 0
                do {
                    var temp1 = 0.0
                    grad.forEachIndexed { index, _ ->
                        grad[index] = calculateWGrad(cs,index)
                        temp1 += Math.pow(grad[index], 2.0)
                    }
                    temp1 = Math.sqrt(temp1)
                    if (temp1 < e1)
                        return cs
                    else{
                        do {
                            cs.forEachIndexed { index, csI ->
                                csNew[index] = csI - t * grad[k] * dirs[k][index]
                            }
                            temp1 = calculateW(csNew) - calculateW(cs)
                            if (temp1 < 0.0) {
                                isStep9 = false
                                temp1 = Math.abs(temp1)
                                var temp2 = 0.0
                                cs.forEachIndexed { index, csI ->
                                    temp2 += Math.pow(csNew[index] - csI, 2.0)

                                }
                                temp2 = Math.sqrt(temp1)
                                if (temp1 < e2 && temp2 < e2) {
                                    if (isPrevOk)
                                        return cs
                                    else {
                                        k++
                                        csNew.forEachIndexed { index, csI ->
                                            cs[index] = csI
                                        }
                                        isPrevOk = true
                                    }
                                } else {
                                    k++
                                    csNew.forEachIndexed { index, csI ->
                                        cs[index] = csI
                                    }
                                }
                            } else {
                                isStep9 = true
                                t /= 2.0
                            }
                            if (t < 1.0E-10)
                                k++
                        }while (isStep9 && t > 1.0E-10)
                        t = tStatic
                    }
                }while (k < n)
                step++
                csNew.forEachIndexed { index, csI ->
                    cs[index] = csI
                }
            }

        }while (stillSearching)
        return cs
    }

    private fun calculateW(cs: Array<Double>): Double {
        var w = 0.0
        pointListOriginal.forEach {
            val yCalculated = calculateRegression(cs, it.u)
            w += Math.pow(it.x - yCalculated, 2.0)
        }
        return w/pointListOriginal.size
    }

    private fun calculateWGrad(cs: Array<Double>,index :Int): Double {
        var w = 0.0
        pointListOriginal.forEach {pointMD ->
            val yCalculated = calculateRegression(cs,pointMD.u)
            val yCalculatedGrad = calculateRegressionGrad(cs, index,pointMD.u)
            w +=  2.0 * (pointMD.x - yCalculated) + yCalculatedGrad
        }
        return w/pointListOriginal.size
    }

    private fun calculateRegressionGrad(cs: Array<Double>, index:Int,u: Array<Double>): Double {
        var temp1 = 0.0
        var temp2 = 0.0
        pointListOriginal.forEach { pointMD ->
            var temp3 = 1.0
            u.forEach { uI ->
                pointMD.u.forEachIndexed { indexForEach, u ->
                    temp3 *= if (indexForEach != index)
                        cores(calculateCore(cs[indexForEach], uI, u))
                    else
                        cores(calculateCoreGrad(cs[indexForEach], uI, u))
                }
            }
            temp1 += temp3
            temp2 += pointMD.x * temp3
        }
        return temp2 / temp1
    }

    private fun calculateCoreGrad(cs: Double, u: Double, uI: Double): Double {
        return -(u - uI) / cs * cs
    }

}