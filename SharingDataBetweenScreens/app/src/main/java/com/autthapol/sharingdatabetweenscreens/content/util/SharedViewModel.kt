package com.autthapol.sharingdatabetweenscreens.content.util

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _sharedState = MutableStateFlow(0)
    val sharedState = _sharedState.asStateFlow()

    init {
        _sharedState.value = savedStateHandle.get<Int>("count") ?: 0
    }

    fun updateState() {
        _sharedState.value++
        savedStateHandle["count"] = _sharedState.value
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel Cleared")
    }
}