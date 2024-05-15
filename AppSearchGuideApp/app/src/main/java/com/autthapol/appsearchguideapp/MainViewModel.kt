package com.autthapol.appsearchguideapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class MainViewModel(
    private val todoSearchManager: TodoSearchManager
) : ViewModel() {

    var state by mutableStateOf(TodoListState())
        private set

    private var searchJobs: Job? = null

    init {
        viewModelScope.launch {
            todoSearchManager.init()
            val todos = (1..100).map {
                Todo(
                    namespace = "my_todos",
                    id = UUID.randomUUID().toString(),
                    score = 1,
                    title = "Todo $it",
                    text = "Description $it",
                    isDone = Random.nextBoolean()
                )
            }
            todoSearchManager.putTodo(todos)
        }
    }

    fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)

        searchJobs?.cancel()
        searchJobs = viewModelScope.launch {
            delay(500L)
            val todos = todoSearchManager.searchTodo(query)
            state = state.copy(todos = todos)
        }
    }

    fun onDoneChange(todo: Todo, isDone: Boolean) {
        viewModelScope.launch {
            todoSearchManager.putTodo(
                listOf(todo.copy(isDone = isDone))
            )
            state = state.copy(
                todos = state.todos.map {
                    if (it.id == todo.id) {
                        it.copy(isDone = isDone)
                    } else it
                }
            )
        }
    }

    override fun onCleared() {
        todoSearchManager.closeSession()
        super.onCleared()
    }
}