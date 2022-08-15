package com.kruna1pate1.pictionaryapp.domain.usecase.game

import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.Game
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectWordUseCase @Inject constructor(
    private val gameRepository: GameRepository
){

    suspend operator fun invoke(roomId: String, pos: Int): Resource<String> {

        return gameRepository.selectWord(roomId, pos)
    }
}