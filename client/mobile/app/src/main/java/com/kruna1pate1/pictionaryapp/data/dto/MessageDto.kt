package com.kruna1pate1.pictionaryapp.data.dto

import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.model.Player

data class MessageDto(
    val player: Player,
    val body: String,
    val time: String
) {
    fun toMessage(): Message {
        return Message(
            player = player,
            body = body,
            time = time
        )
    }

    companion object {
        fun fromMessage(message: Message): MessageDto {
            return MessageDto(
                player = message.player,
                body = message.body,
                time = message.time
            )
        }
    }
}
