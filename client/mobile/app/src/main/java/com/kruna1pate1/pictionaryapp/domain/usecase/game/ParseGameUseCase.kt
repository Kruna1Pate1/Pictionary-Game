package com.kruna1pate1.pictionaryapp.domain.usecase.game

import android.util.Log
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.Game
import com.kruna1pate1.pictionaryapp.domain.model.Score
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import javax.inject.Inject

class ParseGameUseCase @Inject constructor(
    val gson: Gson
) {

    inline operator fun <reified T> invoke(gameDto: GameDto): Game<T> {

//        if (T::class == Score::class) {

            Log.d(TAG, "invoke: ${gameDto.data}")
//        }

        val data = gson.fromJson(gson.toJson(gameDto.data), T::class.java)

        return Game(gameDto.code, data)
    }
}