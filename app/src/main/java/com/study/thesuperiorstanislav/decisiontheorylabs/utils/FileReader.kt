package com.study.thesuperiorstanislav.decisiontheorylabs.utils

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import java.io.BufferedReader

object FileReader {
    fun readTextFromUri(reader: BufferedReader): List<Point>? {
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
}