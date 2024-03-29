package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.CreateRoomDto;
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
public class RoomController {

    private final RoomService roomService;
    private final GameService gameService;

    @MessageMapping("room.get")
    public Flux<RoomDto> getAllRooms(Mono<String> query) {

        return query
                .flatMapMany(q -> Flux.fromIterable(roomService.getAllRoom(q))
                        .map(roomService::roomToDto));
    }


    @MessageMapping("room.create")
    public Mono<RoomDto> createRoom(Mono<CreateRoomDto> createRoomDto) {

        return createRoomDto.map(roomDto -> {
            Room room = gameService.createRoom(roomDto.name(), roomDto.capacity());
            return roomService.roomToDto(room);
        });
    }
}
