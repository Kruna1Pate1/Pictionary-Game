package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.service.ChatService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KRUNAL on 18-05-2022
 */
@Service
public class ChatServiceImpl implements ChatService {

    private final Map<String, Sinks.Many<MessageDto>> chatSinkMap;

    public ChatServiceImpl() {
        this.chatSinkMap = new HashMap<>();
    }

    @Override
    public Flux<MessageDto> getChat(String roomId) {

        return chatSinkMap.get(roomId).asFlux();
    }

    @Override
    public void createChat(String roomId) {

        chatSinkMap.put(roomId, Sinks.many().multicast().directBestEffort());
    }

    @Override
    public void removeChat(String roomId) {

        chatSinkMap.remove(roomId);
    }

    @Override
    public void sendMessage(String roomId, MessageDto messageDto) {

        chatSinkMap.get(roomId).tryEmitNext(messageDto);
    }

}
