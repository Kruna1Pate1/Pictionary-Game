package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.RoomDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.model.enums.ServerCode;
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

    @MessageMapping("room.create")
    public Mono<GameDto<RoomDto>> createRoom(Mono<RoomDto> roomDto) {

        return roomDto.map(roomDto1 -> {
            Room room = gameService.createRoom(roomService.dtoToRoom(roomDto1));
            return roomService.roomToDto(room, ServerCode.GAME_DETAIL);

        }).map(r -> new GameDto<>(ServerCode.GAME_DETAIL, r));
    }
}
