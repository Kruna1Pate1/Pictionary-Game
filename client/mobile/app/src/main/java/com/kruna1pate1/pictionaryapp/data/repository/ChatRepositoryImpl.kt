package com.kruna1pate1.pictionaryapp.data.repository

import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.dto.MessageDto
import com.kruna1pate1.pictionaryapp.data.remote.ChatApi
import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.repository.ChatRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val gson: Gson
) : ChatRepository {

    override fun connectChat(
        roomId: String,
        initialMessage: Message,
        messageFlow: Flow<Message>
    ): Flow<Resource<Message>> {

        val response = chatApi.connectChat(
            roomId,
            MessageDto.fromMessage(initialMessage),
            messageFlow.map { MessageDto.fromMessage(it) }
        )

        return response.map { m ->
            try {
                val data = m.data.readText()
                val messageDto = gson.fromJson(data, MessageDto::class.java)
                Resource.Success(messageDto.toMessage())

            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Error("Can' fetch rooms: ${e.message}")
            }
        }

    }
}