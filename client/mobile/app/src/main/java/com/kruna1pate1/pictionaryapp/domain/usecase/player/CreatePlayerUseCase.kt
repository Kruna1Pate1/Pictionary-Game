package com.kruna1pate1.pictionaryapp.domain.usecase.player

import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.repository.PlayerRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import javax.inject.Inject

class CreatePlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke(name: String): Resource<Player> {

        if (name.isEmpty()) {
            return Resource.Error("Name can't be blank")
        }

        return playerRepository.createOrUpdatePlayer(name)
    }
}