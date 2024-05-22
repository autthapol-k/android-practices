package com.autthapol.sharingdatabetweenscreens.content.util

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersistentViewModel1 @Inject constructor(
    private val sessionCache: SessionCache
) : ViewModel() {

    val session get() = sessionCache.getActiveSession()

    fun saveSession(){
        sessionCache.saveSession(
            session = Session(
                user = User(
                    firstName = "John",
                    lastName = "Doe",
                    email = "john@mail.com"
                ),
                token = "sample-token",
                expiredAt = 1234567890
            )
        )
    }
}