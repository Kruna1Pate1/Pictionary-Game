package com.kruna1pate1.pictionaryapp.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.util.Constants.PLAYER_PREFERENCE_KEY
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Preference @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) {

    private val playerKey = stringPreferencesKey(PLAYER_PREFERENCE_KEY)

    suspend fun savePlayer(player: Player) {
        val jsonData = gson.toJson(player, Player::class.java)

        dataStore.edit {
            it[playerKey] = jsonData
        }
    }

    suspend fun readPlayer(): Player? {

        return try {

            val preferences = dataStore.data.first()
            val jsonData = preferences[playerKey]
            gson.fromJson(jsonData, Player::class.java)

        } catch (e: NoSuchElementException) {
            null
        }
    }
}