package com.kruna1pate1.pictionaryapp.domain.repository

import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    fun getAllRooms(query: String): Flow<Resource<Room>>

    suspend fun createRoom(createRoom: CreateRoom): Resource<Room>
}