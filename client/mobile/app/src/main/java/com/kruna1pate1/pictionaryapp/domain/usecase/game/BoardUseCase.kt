package com.kruna1pate1.pictionaryapp.domain.usecase.game.chat

import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    operator fun invoke(
        roomId: String,
        initialData: DrawData,
        dataFlow: Flow<DrawData>
    ): Flow<Resource<DrawData>> {

        return gameRepository.connectBoard(roomId, initialData, dataFlow)
    }
}