package com.example.learningtesting

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest(){

    @Test
    fun `empty username returns false`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "Johny",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "Raj",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password is empty returns false`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "Ramu",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `confirmed password does not matches password returns false`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "Sohan",
            "123",
            "143"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contain less than 2 digits returns false`(){
        val result= RegistrationUtil.validateRegistrationUtil(
            "",
            "kaksjf2",
            "kaksjf2"
        )
        assertThat(result).isFalse()
    }
}