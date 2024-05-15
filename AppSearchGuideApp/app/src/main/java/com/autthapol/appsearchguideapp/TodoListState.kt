package com.autthapol.appsearchguideapp

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val searchQuery: String = ""
)
