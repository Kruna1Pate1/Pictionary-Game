package com.kruna1pate1.pictionaryapp.data.remote

import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.dto.MessageDto
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
class ChatApi @Inject constructor(
    private val rSocket: RSocket,
    private val gson: Gson
) {

    private val ROUTE = "room.id.chat"


    fun connectChat(roomId: String, initialMessage: MessageDto, messageFlow: Flow<MessageDto>): Flow<Payload> {

        val metadata = RoutingMetadata(ROUTE.replace("id", roomId))

        val initPayload = buildPayload {
            data(gson.toJson(initialMessage, MessageDto::class.java))
            metadata(metadata)
        }

        val payloadFlow: Flow<Payload> = messageFlow.map { m ->
            buildPayload {
                data(gson.toJson(m, MessageDto::class.java))
                metadata(metadata)
            }
        }


        return rSocket.requestChannel(initPayload, payloadFlow)
    }

}