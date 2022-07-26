package com.kruna1pate1.pictionaryapp.domain.repository

import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun connectChat(roomId: String, initialMessage:Message, messageFlow: Flow<Message>): Flow<Resource<Message>>
}