package com.kruna1pate1.pictionaryapp.data.repository

import android.util.Log
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.dto.CreateRoomDto
import com.kruna1pate1.pictionaryapp.data.dto.RoomDto
import com.kruna1pate1.pictionaryapp.data.remote.RoomApi
import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.domain.repository.RoomRepository
import com.kruna1pate1.pictionaryapp.util.Constants
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomApi: RoomApi,
    private val gson: Gson

) : RoomRepository {

    override fun getAllRooms(query: String): Flow<Resource<Room>> {
        val roomFlow = roomApi.getAllRooms(query).map { r ->
            try {
                val data = r.data.readText()
                val roomDto = gson.fromJson(data, RoomDto::class.java)
                Resource.Success(roomDto.toRoom())

            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error("Can' fetch rooms: ${e.message}")
            }
        }

        return roomFlow
    }


    override suspend fun createRoom(createRoom: CreateRoom): Resource<Room> {
        return try {
            val data: String =
                roomApi.createRoom(CreateRoomDto.fromCreateRoom(createRoom)).data.readText()
            Log.d(Constants.TAG, "createRoom: $data")
            val roomDto = gson.fromJson(data, RoomDto::class.java)
            Resource.Success(roomDto.toRoom())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' create room: ${e.message}")
        }
    }
}