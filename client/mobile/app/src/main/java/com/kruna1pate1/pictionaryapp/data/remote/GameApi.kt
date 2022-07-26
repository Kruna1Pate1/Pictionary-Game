package com.kruna1pate1.pictionaryapp.data.remote

import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.util.Resource
import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.metadata
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalMetadataApi::class)
class GameApi @Inject constructor(
    private val rSocket: RSocket,
    private val gson: Gson
) {

    private val ROUTE_ROOM = "room"
    private val ROUTE_CHAT = "chat"

    fun joinRoom(playerId: Int, roomId: String): Flow<Payload> {

        val payload = buildPayload {
            data("$playerId")
            metadata(RoutingMetadata("$ROUTE_ROOM.join.$roomId"))
        }

        return rSocket.requestStream(payload)
    }

    suspend fun leaveRoom(playerId: Int, roomId: String) {

        val payload = buildPayload {
            data("$playerId")
            metadata(RoutingMetadata("$ROUTE_ROOM.left.$roomId"))
        }

        rSocket.fireAndForget(payload)
    }


    fun connectBoard(
        roomId: String,
        initialData: DrawData,
        dataFlow: Flow<DrawData>
    ): Flow<Payload> {

        val metadata = RoutingMetadata("room.$roomId.board")

        val initialPayload = buildPayload {
            data(gson.toJson(initialData, DrawData::class.java))
            metadata(metadata)
        }

        val payloadFlow: Flow<Payload> = dataFlow.map { dd ->
            buildPayload {
                data(gson.toJson(dd, DrawData::class.java))
                metadata(metadata)
            }
        }
        return rSocket.requestChannel(initialPayload, payloadFlow)
    }
}