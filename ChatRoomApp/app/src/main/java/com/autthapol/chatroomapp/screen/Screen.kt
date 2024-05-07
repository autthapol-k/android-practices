package com.autthapol.chatroomapp.screen

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login_screen")
    data object SignUpScreen : Screen("signup_screen")
    data object ChatRoomScreen : Screen("chat_room_screen")
    data object ChatScreen : Screen("chat_screen")
}