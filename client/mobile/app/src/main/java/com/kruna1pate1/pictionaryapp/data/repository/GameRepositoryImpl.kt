package com.kruna1pate1.pictionaryapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.data.remote.GameApi
import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameApi: GameApi,
    private val gson: Gson
) : GameRepository {


    override fun joinRoom(playerId: Int, roomId: String): Flow<Resource<GameDto>> {

        val gameFlow = gameApi.joinRoom(playerId, roomId).map { p ->
            try {
                val data = p.data.readText()
                val gameDto = gson.fromJson(data, GameDto::class.java)
                Resource.Success(gameDto)

            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error("Can' get game update: ${e.message}")
            }
        }

        return gameFlow
    }

    override suspend fun leaveRoom(playerId: Int, roomId: String) {
        gameApi.leaveRoom(playerId, roomId)
    }


    override fun connectBoard(
        roomId: String,
        initialData: DrawData,
        dataFlow: Flow<DrawData>
    ): Flow<Resource<DrawData>> {

        val response = gameApi.connectBoard(roomId, initialData, dataFlow)

        return response.map { d ->
            try {
                val data = d.data.readText()
                val drawData = gson.fromJson(data, DrawData::class.java)
                Resource.Success(drawData)

            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error("Can' fetch draw data: ${e.message}")
            }
        }

    }

    override suspend fun selectWord(roomId: String, pos: Int): Resource<String> {

        return try {
            val data: String = gameApi.selectWord(roomId, pos).data.readText()
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' select word: ${e.message}")
        }
    }

    override suspend fun getPlayers(roomId: String): Resource<List<Player>> {
        return try {
            val data: String = gameApi.getPlayers(roomId).data.readText()

            val type = TypeToken.getParameterized(List::class.java, Player::class.java).type
            val players: List<Player> = gson.fromJson(data, type)
            Resource.Success(players)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' get players: ${e.message}")
        }
    }

    override suspend fun getScores(roomId: String): Resource<Map<Int, Int>> {
        return try {
            val data: String = gameApi.getScores(roomId).data.readText()

            val type = object: TypeToken<Map<Int, Int>>(){}.type
            val scores: Map<Int, Int> = gson.fromJson(data, type)
            Resource.Success(scores)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' get scores: ${e.message}")
        }
    }
}
