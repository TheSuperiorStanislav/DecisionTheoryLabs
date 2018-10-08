package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model

import java.util.*

data class PointMD(val u:Array<Double>,val x:Double) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointMD

        if (!Arrays.equals(u, other.u)) return false
        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(u)
        result = 31 * result + x.hashCode()
        return result
    }
}