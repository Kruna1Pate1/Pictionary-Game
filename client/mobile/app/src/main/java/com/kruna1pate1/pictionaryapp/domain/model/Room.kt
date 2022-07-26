package com.kruna1pate1.pictionaryapp.domain.model

import com.kruna1pate1.pictionaryapp.domain.model.enums.GameStatus

data class Room(
    val roomId: String,
    val name: String,
    val playerCount: Int,
    val capacity: Int,
    val status: GameStatus
)
