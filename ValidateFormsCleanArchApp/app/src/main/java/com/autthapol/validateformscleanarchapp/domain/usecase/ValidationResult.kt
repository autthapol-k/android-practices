package com.autthapol.validateformscleanarchapp.domain.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
