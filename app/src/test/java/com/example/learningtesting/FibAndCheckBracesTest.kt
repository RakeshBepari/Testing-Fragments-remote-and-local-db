package com.example.learningtesting

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FibAndCheckBracesTest{

    @Test
    fun `fibonacci of 4 returns 3`(){
        val result = FibAndCheckBraces.fib(4)

        assertThat(result).isEqualTo(3L)
    }

    @Test
    fun `(a * (b)) should return false`() {
        val result = FibAndCheckBraces.checkBraces(")(a * b)(")
        assertThat(result).isFalse()
    }

    @Test
    fun `(a * b)) should return false`() {
        val result = FibAndCheckBraces.checkBraces("(a * b))")
        assertThat(result).isFalse()
    }

    @Test
    fun `(a * b) should return true`() {
        val result = FibAndCheckBraces.checkBraces("(a * b)")
        assertThat(result).isTrue()
    }

}