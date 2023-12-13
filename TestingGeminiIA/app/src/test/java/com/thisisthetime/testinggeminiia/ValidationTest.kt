package com.thisisthetime.testinggeminiia

import com.thisisthetime.testinggeminiia.screens.Validation
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ValidationTest {
    @Test
    fun testValidEmail() {
        val validEmails = listOf(
            "email@gmail.com",
            "email_1@gmail.com",
            "email.1@gmail.com.mx"
        )
        for (email in validEmails) {
            assertTrue(Validation.isValidEmail(email))
        }
    }

    @Test
    fun testInvalidEmail() {
        val invalidEmails = listOf(
            "not_an_email",
            "email@invaliddomain",
            "email@.com",
            "email@example@com"
        )
        for (email in invalidEmails) {
            assertFalse(Validation.isValidEmail(email))
        }
    }

    @Test
    fun testValidPhoneNumber() {
        val validPhoneNumbers = listOf(
            "+1-234-5678900",
            "+44-789-0123456",
            "+82-106-9876432"
        )
        for (phoneNumber in validPhoneNumbers) {
            assertTrue(Validation.isValidPhoneNumber(phoneNumber))
        }
    }

    @Test
    fun testInvalidPhoneNumber() {
        val invalidPhoneNumbers = listOf(
            "not_a_phone_number",
            "1234567890",
            "+1-234",
            "+1234-567890",
            "+1-234-abc-5678"
        )
        for (phoneNumber in invalidPhoneNumbers) {
            assertFalse(Validation.isValidPhoneNumber(phoneNumber))
        }
    }
}