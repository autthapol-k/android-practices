package com.autthapol.sharingdatabetweenscreens.content.util

data class Session(
    val user: User,
    val token: String,
    val expiredAt: Long
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String
)
