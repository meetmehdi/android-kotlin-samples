package com.example.lmorda

import com.example.lmorda.utils.thousandsToKs
import org.junit.Test
import org.junit.Assert.*

//TODO: Add more unit tests!
class UtilTests {

    @Test
    fun thousands_OneThousand() {
        assertEquals(1000.thousandsToKs(), "1.0k")
    }

}