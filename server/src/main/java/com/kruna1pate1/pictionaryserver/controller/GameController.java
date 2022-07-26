package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.DrawData;
import com.kruna1pate1.pictionaryserver.service.BoardService;
import com.kruna1pate1.pictionaryserver.service.ChatService;
import com.kruna1pate1.pictionaryserver.service.Impl.GameService;
import com.kruna1pate1.pictionaryserver.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by KRUNAL on 20-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final RoomService roomService;
    private final GameService gameService;
    private final ChatService chatService;
    private final BoardService boardService;

    @MessageMapping("room.join.{id}")
    public Flux<GameDto> joinRoom(Mono<Integer> playerId, @DestinationVariable("id") String roomId) {

        playerId.subscribe(id -> {
            try {
                gameService.joinRoom(roomId, id);
            } catch (Exception e) {
                throw new RuntimeException("Can't join. " + e.getMessage());
            }
        });

        return roomService.getRoomStream(roomId);
    }

    @MessageMapping("room.left.{id}")
    public void leftRoom(Mono<Integer> playerId, @DestinationVariable("id") String roomId) {

        playerId.subscribe(id -> {
            try {
                gameService.leaveRoom(roomId, id);
            } catch (RoomNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @MessageMapping("room.{id}")
    public void broadcast(Mono<GameDto> gameDto, @DestinationVariable("id") String roomId) {

        gameDto.subscribe(g -> {

            switch (g.code()) {

                case GAME_DETAIL -> {
                    try {
                        roomService.broadcastRoomDetails(roomId);
                    } catch (RoomNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case PLAYERS -> {
                }
                case SCORES -> {
                }
                case SELECT_WORD -> {
                }
                case ROUND -> {
                }
                case HINT -> {
                }
            }

        });
    }
    @MessageMapping("room.{id}.chat")
    public Flux<MessageDto> getChat(Flux<MessageDto> messageDto, @DestinationVariable("id") String roomId) {

        return messageDto
                .doOnNext(message -> chatService.sendMessage(roomId, message))
                .switchMap(m -> chatService.getChat(roomId));
    }


    @MessageMapping("room.{id}.board")
    public Flux<DrawData> draw(Flux<DrawData> drawData, @DestinationVariable("id") String roomId) {

        return drawData
                .log()
                .doOnNext(dd -> boardService.sendDrawData(roomId, dd))
                .switchMap(dd -> boardService.getBoard(roomId));
    }

}
