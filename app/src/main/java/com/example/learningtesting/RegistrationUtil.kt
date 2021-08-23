package com.example.learningtesting

object RegistrationUtil {
    private val existingListOfUsers= listOf("Raj","Rohan")

    /**
     * the input is not valid if..
     *  ... the username/password is empty
     *  ... the username is already taken
     *  ... the confirmed password is not same as password
     *  ... the password contains less than 2 digits
     */

    fun validateRegistrationUtil(
        username:String,
        password:String,
        confirmedPassword:String
    ):Boolean{
        if (username.isEmpty() || password.isEmpty())
            return false
        if (username in existingListOfUsers)
            return false
        if (password!=confirmedPassword)
            return false
        if (password.count{it.isDigit()}<2)
            return false
        return true
    }
}