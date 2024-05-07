package com.autthapol.wishlistapp

import android.content.Context
import androidx.room.Room
import com.autthapol.wishlistapp.data.WishDatabase
import com.autthapol.wishlistapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val withRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}