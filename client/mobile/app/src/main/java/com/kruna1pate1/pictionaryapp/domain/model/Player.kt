package com.kruna1pate1.pictionaryapp.domain.model

import com.kruna1pate1.pictionaryapp.domain.model.enums.PlayerStatus

data class Player(

    val id: Int,
    val name : String,
    val status: PlayerStatus
)
