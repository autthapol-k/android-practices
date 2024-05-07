package com.autthapol.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "wish-title") val title: String = "",
    @ColumnInfo(name = "wish-desc") val desc: String = ""
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Google Watch 2", desc = "An Android Watch"),
        Wish(title = "Oculus Quest 2", desc = "A VR headset for playing games"),
        Wish(title = "A Sci-fa, Book", desc = "A science friction book from any best seller"),
        Wish(title = "Bean bag", desc = "A comfy bean bag to substitute for a chair")
    )
}