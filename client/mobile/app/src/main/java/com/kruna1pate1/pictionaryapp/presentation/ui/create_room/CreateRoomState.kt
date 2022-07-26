package com.kruna1pate1.pictionaryapp.presentation.ui.create_room

import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom

data class CreateRoomState(
    val name: String = "",
    val capacity: String = "4",
    val isLoading: Boolean = false
)