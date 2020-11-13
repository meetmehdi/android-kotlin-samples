package com.example.lmorda

import com.example.lmorda.utils.Utils
import org.junit.Test
import org.junit.Assert.*

//TODO: Add more unit tests!
class UtilTests {

    @Test
    fun thousands_OneThousand() {
        assertEquals(Utils.thousandsToKs(1000), "1.0k")
    }

}