package com.study.thesuperiorstanislav.decisiontheorylabs.utils.Math

import net.objecthunter.exp4j.ExpressionBuilder

object Function {
    fun calculateFunc(u: Double, function: String): Double {
        val exp = ExpressionBuilder(function)
                .variables("u")
                .build()
                .setVariable("u", u)
        return exp.evaluate()
    }
}