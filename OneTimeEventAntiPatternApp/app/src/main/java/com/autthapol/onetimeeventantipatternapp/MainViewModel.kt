package com.autthapol.onetimeeventantipatternapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val navigationChannel = Channel<NavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>(
        // fix shared flow lost if none of active collector
        // three events should be cached if no collector
        replay = 3
    )
    val navigationEventsSharedFlow = _navigationEvents.asSharedFlow()

    var isLoggedIn by mutableStateOf(false)
        private set

    var state by mutableStateOf(LoginState())
        private set

    fun login() {
        viewModelScope.launch {
            repeat(1000) {
                delay(3L)
                navigationChannel.send(NavigationEvent.CountEvent(it))
            }
        }
//        viewModelScope.launch {
//            state = state.copy(isLoading = true)
//            delay(3000L)
//
////            navigationChannel.send(NavigationEvent.NavigateToProfile)
//
//            _navigationEvents.emit(NavigationEvent.NavigateToProfile)
//
//
//            state = state.copy(
//                isLoading = false,
//                isLoggedIn = true,
//                onConsumed = {
//                    // reset state
//                    state = state.copy(isLoggedIn = false)
//                }
//            )
//        }
    }
}

sealed interface NavigationEvent {
    data object NavigateToProfile : NavigationEvent
    data class CountEvent(val count: Int) : NavigationEvent
}

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val onConsumed: () -> Unit = {}
)