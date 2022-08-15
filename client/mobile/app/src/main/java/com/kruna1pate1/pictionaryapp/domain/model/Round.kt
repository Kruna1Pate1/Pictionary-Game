package com.kruna1pate1.pictionaryapp.domain.model

import com.kruna1pate1.pictionaryapp.domain.model.enums.RoundStatus

data class Round(

    val wordGroup: WordGroup,
    val drawer: Player,
    val status: RoundStatus,
    val timeRemain: Float
)
