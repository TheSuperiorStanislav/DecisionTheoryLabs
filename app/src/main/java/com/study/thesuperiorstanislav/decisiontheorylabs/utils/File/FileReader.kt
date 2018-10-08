package com.study.thesuperiorstanislav.decisiontheorylabs.utils.File

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import java.io.BufferedReader

object FileReader {
    fun readPointFromUri(reader: BufferedReader): List<Point>? {
        try {
            val pointList = arrayListOf<Point>()
            var line: String?
            line = reader.readLine()
            while (line != null) {
                val unknownSym1 = 65533.toChar().toString()
                val unknownSym2 = 0.toChar().toString()
                val cord = line
                        .replace(unknownSym1, "")
                        .replace(unknownSym2, "")
                        .replace(",", ".")
                        .replace("\t", " ")
                        .split(" ")
                if (!cord[0].isEmpty())
                    pointList.add(Point(cord[0].toDouble(),cord[1].toDouble()))
                line = reader.readLine()
            }
            return pointList
        }catch (e: Exception){
            return null
        }
    }

    fun readPointMDFromUri(reader: BufferedReader): List<PointMD>? {
        try {
            val pointList = arrayListOf<PointMD>()
            var line: String?
            line = reader.readLine()
            while (line != null) {
                val unknownSym1 = 65533.toChar().toString()
                val unknownSym2 = 0.toChar().toString()
                val cord = line
                        .replace(unknownSym1, "")
                        .replace(unknownSym2, "")
                        .replace(",", ".")
                        .replace("\t", " ")
                        .split(" ")
                if (!cord[0].isEmpty()){
                    val uList = mutableListOf<Double>()
                    var x = 0.0
                    cord.forEachIndexed { index, s ->
                        if (index == cord.size - 1)
                            x = s.toDouble()
                        else
                            uList.add(s.toDouble())
                    }

                    pointList.add(PointMD(uList.toTypedArray(),x))
                }
                line = reader.readLine()
            }
            return pointList
        }catch (e: Exception){
            return null
        }
    }
}