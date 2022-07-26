package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.PlayerDto;
import com.kruna1pate1.pictionaryserver.exception.PlayerNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @MessageMapping("player.get")
    public Mono<PlayerDto> getPlayer(Mono<Integer> id) throws PlayerNotFoundException {
        return id.map(p ->
                {
                    try {
                        return playerService.getPlayer(p);
                    } catch (PlayerNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).map(playerService::playerToDto);
    }

    @MessageMapping("player.create")
    public Mono<PlayerDto> addPlayer(Mono<String> name) {

        return name.map(playerService::createPlayer)
                .map(playerService::playerToDto);
    }

    @MessageMapping("player.update")
    public Mono<PlayerDto> updatePlayer(Mono<PlayerDto> player) {

        return player.map(playerDto -> {
            Player p = playerService.dtoToPlayer(playerDto);
            return playerService.playerToDto(
                    playerService.updatePlayer(p)
            );
        });
    }

    @MessageMapping("player.delete")
    public Mono<PlayerDto> removePlayer(Mono<Integer> id) throws PlayerNotFoundException {
        return id.map(p ->
                {
                    try {
                        return playerService.removePlayer(p);
                    } catch (PlayerNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).map(playerService::playerToDto);
    }


}
