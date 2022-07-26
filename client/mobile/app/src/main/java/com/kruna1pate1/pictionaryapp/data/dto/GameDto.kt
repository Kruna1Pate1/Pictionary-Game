package com.kruna1pate1.pictionaryapp.data.dto

import com.kruna1pate1.pictionaryapp.domain.model.Game
import com.kruna1pate1.pictionaryapp.domain.model.enums.ServerCode

data class GameDto(
    val code: ServerCode,
    val data: Any
) {
    fun <T> toGame(): Game<T> {
        return Game(
            code = code,
            data = data as T
        )
    }
}
