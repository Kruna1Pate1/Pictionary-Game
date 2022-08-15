package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.dto.PlayerDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.DrawData;
import com.kruna1pate1.pictionaryserver.service.*;
import com.kruna1pate1.pictionaryserver.service.Impl.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by KRUNAL on 20-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final RoomService roomService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final ChatService chatService;
    private final BoardService boardService;
    private final LeaderboardService leaderboardService;

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

    @MessageMapping("room.{id}.chat")
    public Flux<MessageDto> getChat(Flux<MessageDto> messageDto, @DestinationVariable("id") String roomId) {

        return messageDto
                .doOnNext(message -> chatService.sendMessage(roomId, message))
                .switchMap(m -> chatService.getChat(roomId));
    }


    @MessageMapping("room.{id}.board")
    public Flux<DrawData> draw(Flux<DrawData> drawData, @DestinationVariable("id") String roomId) {

        return drawData
//                .log()
                .doOnNext(dd -> boardService.sendDrawData(roomId, dd))
                .switchMap(dd -> boardService.getBoard(roomId));
    }


    @MessageMapping("room.{id}.word")
    public Mono<String> selectWord(Mono<Integer> pos, @DestinationVariable("id") String id) {

        return pos.map(p -> roomService.selectWord(id, p));
//                .doOnNext(ignored -> roomService.broadcastRound(id));
    }

    @MessageMapping("room.{id}.players")
    public Mono<List<PlayerDto>> getPlayers(@DestinationVariable("id") String id) {

        return Mono.just(
                Arrays.stream(roomService.getRoom(id).getPlayers())
                        .map(playerService::playerToDto)
                        .toList()
        );
    }

    @MessageMapping("room.{id}.scores")
    public Mono<Map<Integer, Integer>> getScores(@DestinationVariable("id") String id) {

        return Mono.just(leaderboardService.getLeaderboard(id).getScores());
    }


}
