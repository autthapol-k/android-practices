package com.autthapol.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TestActionBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "TEST_ACTION") {
            println("Received test intent!")
        }
    }
}