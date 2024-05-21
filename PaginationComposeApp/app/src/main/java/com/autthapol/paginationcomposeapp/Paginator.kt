package com.autthapol.paginationcomposeapp

interface Paginator<Key,Item> {
    suspend fun loadNextItem()
    fun reset()
}