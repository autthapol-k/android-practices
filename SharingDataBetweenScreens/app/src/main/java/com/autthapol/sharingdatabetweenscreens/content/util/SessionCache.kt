package com.autthapol.sharingdatabetweenscreens.content.util

interface SessionCache {
    fun saveSession(session: Session)
    fun getActiveSession(): Session?
    fun clearSession()
}