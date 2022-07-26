package com.kruna1pate1.pictionaryapp.domain.usecase.game.chat

import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.repository.ChatRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(roomId: String, initialMessage: Message, messageFlow: Flow<Message>): Flow<Resource<Message>> {

        return chatRepository.connectChat(roomId,initialMessage, messageFlow)
    }
}