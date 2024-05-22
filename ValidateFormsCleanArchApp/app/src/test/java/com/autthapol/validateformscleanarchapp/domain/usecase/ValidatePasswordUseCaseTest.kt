package com.autthapol.validateformscleanarchapp.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var useCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        useCase = ValidatePasswordUseCase()
    }

    @Test
    fun `password is letter-only, returns error`() {
        val result = useCase.execute("abcdefgh")

        assertEquals(result.successful,false)
    }
}