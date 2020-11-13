package com.example.lmorda.utils

import android.view.View

object Utils {

    //TODO: Add unit tests
    fun thousandsToKs(number: Int): String {
        return if (number > 0) (number / 1000).toString() + "." + ((number % 1000) / 100).toString() + "k"
        else number.toString()
    }

    var View.visible: Boolean
        get() = visibility == View.VISIBLE
        set(visible) {
            visibility = if (visible) View.VISIBLE else View.GONE
        }

}