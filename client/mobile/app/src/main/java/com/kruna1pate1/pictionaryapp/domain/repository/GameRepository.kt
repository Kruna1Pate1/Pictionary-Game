package com.kruna1pate1.pictionaryapp.domain.repository

import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun joinRoom(playerId: Int, roomId: String): Flow<Resource<GameDto>>

    suspend fun leaveRoom(playerId: Int, roomId: String)

    fun connectBoard(roomId: String, initialData: DrawData, dataFlow: Flow<DrawData>): Flow<Resource<DrawData>>

    suspend fun selectWord(roomId: String, pos: Int): Resource<String>
    suspend fun getPlayers(roomId: String): Resource<List<Player>>

    suspend fun getScores(roomId: String):
            Resource<Map<Int, Int>>
}