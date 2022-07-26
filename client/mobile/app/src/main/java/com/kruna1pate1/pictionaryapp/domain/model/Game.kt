package com.kruna1pate1.pictionaryapp.domain.model

import com.kruna1pate1.pictionaryapp.domain.model.enums.ServerCode

data class Game<T>(
    val code: ServerCode,
    val data: T
) {
}
