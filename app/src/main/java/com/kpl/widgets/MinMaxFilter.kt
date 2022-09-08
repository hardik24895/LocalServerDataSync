package com.kpl.widgets

import android.text.InputFilter
import android.text.Spanned


class MinMaxFilter(min: Int, max: Int) : InputFilter {


    var mIntMin: Int = 0
    var mIntMax: Int = 0

    init {
        this.mIntMin = min
        this.mIntMax = max
    }


    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(mIntMin, mIntMax, input)) return null
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c >= a && c <= b else c >= b && c <= a
    }
}