package com.kruna1pate1.pictionaryapp.domain.usecase.game

import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import javax.inject.Inject

class LeaveRoomUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(playerId: Int, roomId: String) {
        gameRepository.leaveRoom(playerId, roomId)
    }
}