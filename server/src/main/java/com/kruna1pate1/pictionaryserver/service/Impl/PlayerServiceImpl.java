package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.PlayerDto;
import com.kruna1pate1.pictionaryserver.exception.PlayerNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by KRUNAL on 18-05-2022
 */
@Slf4j
@Service
public class PlayerServiceImpl implements com.kruna1pate1.pictionaryserver.service.PlayerService {

    private final ModelMapper modelMapper;
    private final Map<Integer, Player> players;
    private final AtomicInteger atomicInteger;

    public PlayerServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.players = new HashMap<>();
        this.atomicInteger = new AtomicInteger(-1);
    }

    @Override
    public List<Player> getAllPlayers() {
        return players.values().stream().toList();
    }

    @Override
    public Player getPlayer(int id) throws PlayerNotFoundException {
        Player player = players.get(id);
        if (player != null) {
            return player;
        } else {
            throw new PlayerNotFoundException("Player not found");
        }
    }

    @Override
    public Player addPlayer(Player player) {
        player.setId(atomicInteger.incrementAndGet());
        return players.put(player.getId(), player);
    }

    @Override
    public Player removePlayer(int id) throws PlayerNotFoundException {
        Player player = players.remove(id);
        if (player != null) {
            return player;
        } else {
            throw new PlayerNotFoundException("Player not found");
        }
    }

    @Override
    public void removeAllPlayers() {

        players.clear();
    }


    @Override
    public Player dtoToPlayer(PlayerDto playerDto) {

        return modelMapper.map(playerDto, Player.class);
    }

    @Override
    public PlayerDto playerToDto(Player player) {

        return modelMapper.map(player, PlayerDto.class);
    }
}
