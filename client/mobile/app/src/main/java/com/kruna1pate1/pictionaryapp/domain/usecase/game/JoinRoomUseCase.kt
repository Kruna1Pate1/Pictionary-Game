package com.kruna1pate1.pictionaryapp.domain.usecase.game

import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.Game
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JoinRoomUseCase @Inject constructor(
    private val gameRepository: GameRepository
){

    operator fun invoke(playerId: Int, roomId: String): Flow<Resource<GameDto>> {

        return gameRepository.joinRoom(playerId, roomId)
    }
}