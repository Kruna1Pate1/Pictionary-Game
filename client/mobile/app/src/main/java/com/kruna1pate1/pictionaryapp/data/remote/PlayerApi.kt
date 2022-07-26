package com.kruna1pate1.pictionaryapp.data.remote

import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.domain.model.Player
import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.metadata
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import javax.inject.Inject

@OptIn(ExperimentalMetadataApi::class)
class PlayerApi @Inject constructor(
    private val rSocket: RSocket,
    private val gson: Gson
) {

    private val ROUTE = "player"

    suspend fun createPlayer(name: String): Payload {

        val payload = buildPayload {
            data(name)
            metadata(RoutingMetadata("$ROUTE.create"))
        }

        return rSocket.requestResponse(payload)

    }

    suspend fun updatePlayer(player: Player): Payload {

        val payload = buildPayload {
            data(gson.toJson(player, Player::class.java))
            metadata(RoutingMetadata("$ROUTE.update"))
        }

        return rSocket.requestResponse(payload)
    }

    suspend fun getPlayerById(id: Int): Payload {

        val payload = buildPayload {
            data("$id")
            metadata(RoutingMetadata("$ROUTE.get"))
        }

        return rSocket.requestResponse(payload)
    }

    suspend fun deletePlayerById(id: Int): Payload {

        val payload = buildPayload {
            data("$id")
            metadata(RoutingMetadata("$ROUTE.delete"))
        }

        return rSocket.requestResponse(payload)
    }
}