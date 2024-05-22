package com.autthapol.validateformscleanarchapp.domain.usecase

class ValidatePasswordUseCase {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "This password need to consist of at least 8 characters"
            )
        }
        val containsLettersAsDigits = password.any { it.isDigit() }
                && password.any { it.isLetter() }
        if (!containsLettersAsDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and one digit"
            )
        }

        return ValidationResult(successful = true)
    }
}