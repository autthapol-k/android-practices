package com.autthapol.validateformscleanarchapp.domain.usecase

class ValidateTermsUseCase {

    fun execute(acceptedTerms: Boolean): ValidationResult {
        if (!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }

        return ValidationResult(successful = true)
    }
}