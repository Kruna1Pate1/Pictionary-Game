package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 20-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

//    private final ChatService chatService;
//
//    @MessageMapping("room.{id}.chat")
//    public Flux<MessageDto> getChat(Flux<MessageDto> messageDto, @DestinationVariable("id") String roomId) {
//
//        return messageDto
//                .doOnNext(message -> chatService.sendMessage(roomId, message))
//                .switchMap(m -> chatService.getChat(roomId));
//    }
}
