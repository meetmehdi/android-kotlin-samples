package com.example.lmorda.utils

object Utils {

    //TODO: Add unit tests
    fun thousandsToKs(number: Int): String {
        return if (number > 0) (number / 1000).toString() + "." + ((number % 1000) / 100).toString() + "k"
        else number.toString()
    }

}