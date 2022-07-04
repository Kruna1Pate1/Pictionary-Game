package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 18-05-2022
 */
public interface ChatService {

    Flux<MessageDto> getChat(String roomId);

    void createChat(String roomId);

    void removeChat(String roomId);

    void sendMessage(String roomId, MessageDto messageDto);
}
