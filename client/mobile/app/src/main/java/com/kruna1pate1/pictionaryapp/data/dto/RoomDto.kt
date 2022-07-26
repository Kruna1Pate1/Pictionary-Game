package com.kruna1pate1.pictionaryapp.data.dto

import com.kruna1pate1.pictionaryapp.domain.model.enums.GameStatus
import com.kruna1pate1.pictionaryapp.domain.model.Room

data class RoomDto(
    val roomId: String,
    val name: String,
    val playerCount: Int,
    val capacity: Int,
    val status: GameStatus
) {

    fun toRoom(): Room {

        return Room(
            roomId = roomId,
            name = name,
            playerCount = playerCount,
            capacity = capacity,
            status = status
        )
    }
}
