package com.kruna1pate1.pictionaryapp.data.repository

import android.util.Log
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.preference.Preference
import com.kruna1pate1.pictionaryapp.data.remote.PlayerApi
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.repository.PlayerRepository
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import com.kruna1pate1.pictionaryapp.util.Resource
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerApi: PlayerApi,
    private val preference: Preference,
    private val gson: Gson
) : PlayerRepository {


    override suspend fun createOrUpdatePlayer(name: String): Resource<Player> {
        val player = preference.readPlayer()

        val result = if (player != null) {
            updatePlayer(player.copy(name = name))

        } else {
            createPlayer(name)
        }

        result.data?.let { p ->
            Log.d(TAG, "createOrUpdatePlayer: $p")
            preference.savePlayer(p)
        }

        return result
    }

    private suspend fun createPlayer(name: String): Resource<Player> {

        return try {
            val data: String = playerApi.createPlayer(name).data.readText()
            Log.d(TAG, "createPlayer: $data")
            val player = gson.fromJson(data, Player::class.java)
            Resource.Success(player)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' create user: ${e.message}")
        }

    }

    private suspend fun updatePlayer(player: Player): Resource<Player> {
        return try {
            val data: String = playerApi.updatePlayer(player).data.readText()
            val updatedPlayer = gson.fromJson(data, Player::class.java)
            Resource.Success(updatedPlayer)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' create user: ${e.message}")
        }

    }

    override suspend fun getPlayer(): Resource<Player> {

        var player = preference.readPlayer()
        return try {

            player?.let {
                val data: String = playerApi.getPlayerById(it.id).data.readText()
                player = gson.fromJson(data, Player::class.java)
                preference.savePlayer(player!!)
            }
            Resource.Success(player!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' fetch player")
        }

    }

    override suspend fun deletePlayer(): Resource<Player> {

        val player = preference.readPlayer()

        return try {
            val dataByte: String =
                playerApi.deletePlayerById(player!!.id).data.readByte().toString()
            val data = gson.fromJson(dataByte, Player::class.java)
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can' delete player: ${e.message}")
        }

    }

}