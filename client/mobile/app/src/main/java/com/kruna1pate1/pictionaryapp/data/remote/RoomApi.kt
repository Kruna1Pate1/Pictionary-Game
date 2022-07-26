package com.kruna1pate1.pictionaryapp.data.remote

import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.dto.CreateRoomDto
import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.metadata
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalMetadataApi::class)
class RoomApi @Inject constructor(
    private val rSocket: RSocket,
    private val gson: Gson
) {

    private val ROUTE = "room"


    fun getAllRooms(query: String): Flow<Payload> {

        val payload = buildPayload {

            data(
                query.ifEmpty { " " }
            )
            metadata(RoutingMetadata("$ROUTE.get"))
        }

        return rSocket.requestStream(payload)
    }

    fun joinRoom(playerId: Int, roomId: Int): Flow<Payload> {

        val payload = buildPayload {
            data("$playerId")
            metadata(RoutingMetadata("$ROUTE.join.$roomId"))
        }

        return rSocket.requestStream(payload)
    }

    suspend fun createRoom(createRoomDto: CreateRoomDto): Payload {

        val payload = buildPayload {
            data(gson.toJson(createRoomDto, CreateRoomDto::class.java))
            metadata(RoutingMetadata("$ROUTE.create"))
        }

        return rSocket.requestResponse(payload)
    }

}