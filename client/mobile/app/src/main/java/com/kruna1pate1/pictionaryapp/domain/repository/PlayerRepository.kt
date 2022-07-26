package com.kruna1pate1.pictionaryapp.domain.repository

import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.util.Resource

interface PlayerRepository {

    suspend fun createOrUpdatePlayer(name: String): Resource<Player>

    suspend fun getPlayer(): Resource<Player>

    suspend fun deletePlayer(): Resource<Player>
}