package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.PlayerDto;
import com.kruna1pate1.pictionaryserver.exception.PlayerNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Player;

import java.util.List;

/**
 * Created by KRUNAL on 21-05-2022
 */
public interface PlayerService {
    List<Player> getAllPlayers();

    Player getPlayer(int id) throws PlayerNotFoundException;

    Player createPlayer(String name);

    Player updatePlayer(Player player);

    Player removePlayer(int id) throws PlayerNotFoundException;

    void removeAllPlayers();

    Player dtoToPlayer(PlayerDto playerDto);

    PlayerDto playerToDto(Player player);
}
