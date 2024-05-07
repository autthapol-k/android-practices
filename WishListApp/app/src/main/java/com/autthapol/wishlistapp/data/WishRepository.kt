package com.autthapol.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {
    fun getWishes(): Flow<List<Wish>> {
        return wishDao.getAll()
    }

    fun getWish(id: Long): Flow<Wish> {
        return wishDao.getById(id)
    }

    suspend fun addWish(wish: Wish) {
        wishDao.add(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.update(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.delete(wish)
    }
}