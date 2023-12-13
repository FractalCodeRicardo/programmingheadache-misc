package com.thisisthetime.testinggeminiia.screens

class Validation {
    companion object {
        fun isValidEmail(email: String): Boolean {
            val regex = Regex("^([a-zA-Z0-9_\\.\\-\\+]+)@([a-zA-Z0-9\\-]+)\\.([a-zA-Z]{2,6})(\\.[a-zA-Z]{2,6})?$")
            return regex.matches(email)
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            val regex = Regex("^\\+\\d{1,2}-\\d{3}-\\d{7}$")
            return regex.matches(phoneNumber)
        }

    }
}