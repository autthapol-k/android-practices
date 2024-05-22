package com.autthapol.validateformscleanarchapp.domain.usecase

class ValidateRepeatedPasswordUseCase {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "This passwords doesn't match"
            )
        }

        return ValidationResult(successful = true)
    }
}