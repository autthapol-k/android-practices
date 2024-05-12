package com.example.android.hilt.data

import dagger.hilt.android.scopes.ActivityScoped
import java.util.LinkedList
import javax.inject.Inject
@ActivityScoped // can remove this because we define annotation to remove ambiguous
class LoggerInMemoryDataSource @Inject constructor() : LoggerDataSource {
    private val logs = LinkedList<Log>()
    override fun addLog(msg: String) {
        logs.add(Log(msg, System.currentTimeMillis()))
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }

    override fun removeLogs() {
        logs.clear()
    }
}