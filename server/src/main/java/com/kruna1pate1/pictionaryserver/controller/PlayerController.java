package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.PlayerDto;
import com.kruna1pate1.pictionaryserver.exception.PlayerNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by KRUNAL on 19-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @MessageMapping("player.get.all")
    public Flux<PlayerDto> getAllPlayers() {
        return Flux.fromIterable(playerService.getAllPlayers())
                .map(playerService::playerToDto);
    }

    @MessageMapping("player.get.{id}")
    public Mono<PlayerDto> getPlayer(@DestinationVariable("id") int id) throws PlayerNotFoundException {
        Player player = playerService.getPlayer(id);

        PlayerDto playerDto = playerService.playerToDto(player);
        return Mono.just(playerDto);
    }

    @MessageMapping("player.create")
    public Mono<Boolean> addPlayer(Mono<PlayerDto> player) {

        return player.map(playerService::dtoToPlayer)
                .doOnNext(player1 -> log.debug(player1.toString()))
                .map(player1 -> playerService.addPlayer(player1) != null);
    }

    @MessageMapping("player.delete.{id}")
    public Mono<PlayerDto> removePlayer(@DestinationVariable("id") int id) throws PlayerNotFoundException {

        return Mono.just(playerService.removePlayer(id))
                .map(playerService::playerToDto);
    }


}
