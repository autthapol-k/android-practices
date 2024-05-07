package com.autthapol.chatroomapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoomRepository(private val firestore: FirebaseFirestore) {
    suspend fun createRoom(name: String): Result<Unit> = try {
        val room = Room(name = name)
        firestore.collection("rooms").add(room).await()
        Result.Success(Unit)
    } catch (ex: Exception) {
        Result.Error(ex)
    }

    suspend fun getRooms(): Result<List<Room>> = try {
        val querySnapshot = firestore.collection("rooms").get().await()
        val rooms = querySnapshot.documents.mapNotNull { document ->
            document.toObject(Room::class.java)?.copy(id = document.id)
        }
        Result.Success(rooms)
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}