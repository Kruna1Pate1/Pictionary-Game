package com.kruna1pate1.pictionaryapp.data.dto

import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom

data class CreateRoomDto(
    val name: String,
    val capacity: Int
) {
    fun toCreateRoom(): CreateRoom {

        return CreateRoom(
            name = name,
            capacity = capacity
        )
    }

    companion object {
        fun fromCreateRoom(createRoom: CreateRoom): CreateRoomDto {

            return CreateRoomDto(
                name = createRoom.name,
                capacity = createRoom.capacity
            )
        }
    }
}