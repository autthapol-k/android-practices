package com.autthapol.chatroomapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autthapol.chatroomapp.Injection
import com.autthapol.chatroomapp.data.Message
import com.autthapol.chatroomapp.data.MessageRepository
import com.autthapol.chatroomapp.data.Result
import com.autthapol.chatroomapp.data.User
import com.autthapol.chatroomapp.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {

    private val messageRepository: MessageRepository = MessageRepository(Injection.instance())
    private val userRepository: UserRepository = UserRepository(
        FirebaseAuth.getInstance(),
        Injection.instance()
    )

    init {
        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> _currentUser.value = result.data
                is Result.Error -> {
                }
            }
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            if (_roomId.value?.isNotEmpty() == true) {
                messageRepository.getChatMessage(_roomId.value.toString())
                    .collect {
                        _messages.value = it
                    }
            }
        }
    }

    fun sendMessage(text: String) {
        if (_currentUser.value != null) {
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is Result.Success -> Unit
                    is Result.Error -> {

                    }
                }
            }
        }
    }

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }
}