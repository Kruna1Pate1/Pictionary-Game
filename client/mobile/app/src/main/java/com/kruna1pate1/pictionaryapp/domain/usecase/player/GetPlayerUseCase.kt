package com.kruna1pate1.pictionaryapp.domain.usecase.player

import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.repository.PlayerRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke(): Resource<Player> {

        return playerRepository.getPlayer()
    }
}