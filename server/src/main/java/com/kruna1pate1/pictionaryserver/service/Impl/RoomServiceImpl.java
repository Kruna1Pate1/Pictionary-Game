package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.RoomDto;
import com.kruna1pate1.pictionaryserver.dto.RoundDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.exception.RoomOutOfCapacityException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.model.enums.GameStatus;
import com.kruna1pate1.pictionaryserver.model.enums.ServerCode;
import com.kruna1pate1.pictionaryserver.service.LeaderboardService;
import com.kruna1pate1.pictionaryserver.service.PlayerService;
import com.kruna1pate1.pictionaryserver.service.RoomService;
import com.kruna1pate1.pictionaryserver.service.RoundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by KRUNAL on 18-05-2022
 */
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final ModelMapper modelMapper;
    private final RoundService roundService;
    private final Map<String, Room> rooms;
    private final Map<String, Sinks.Many<GameDto>> roomSinkMap;


    public RoomServiceImpl(ModelMapper modelMapper, @Lazy RoundService roundService) {
        this.modelMapper = modelMapper;
        this.roundService = roundService;
        this.rooms = new HashMap<>();
        this.roomSinkMap = new HashMap<>();
    }


    @Override
    public Room getRoom(String id) throws RoomNotFoundException {

        Room room = rooms.get(id);

        if (room != null) {
            return room;
        } else {
            throw new RoomNotFoundException("Room not found");
        }
    }

    @Override
    public List<Room> getAllRoom(String query) {


        return rooms.values()
                .stream()
                .filter(r ->
                        query.isBlank() || r.getName().contains(query))
                .toList();
    }

    @Override
    public List<Room> getRoomStartsWith(String s) {
        return rooms.values()
                .stream()
                .filter(room -> room.getName().startsWith(s))
                .collect(Collectors.toList());
    }

    @Override
    public Room createRoom(String name, int capacity) {

        Room room = new Room(capacity);
        String id;
        do {
            id = RandomStringUtils.randomAlphabetic(6).toUpperCase();

        } while (rooms.containsKey(id));

        room.setName(name);
        room.setId(id);
        room.setStatus(GameStatus.INITIAL);
        rooms.put(id, room);
        roomSinkMap.put(id, Sinks.many().multicast().directBestEffort());

        return room;
    }

    @Override
    public Room removeRoom(String id) throws RoomNotFoundException {

        Room room = rooms.remove(id);

        if (room != null) {
            roomSinkMap.remove(id);
            return room;
        } else {
            throw new RoomNotFoundException("Room not found");
        }
    }

    @Override
    public void removeAll() {

        rooms.clear();
        roomSinkMap.clear();
    }

    @Override
    public Room addPlayer(String roomId, Player player) throws RoomNotFoundException, RoomOutOfCapacityException {
        Room room = getRoom(roomId);


        if (!room.addPlayer(player)) {
            throw new RoomOutOfCapacityException("Room is already full");
        }

        room.getLeaderboard().addOrUpdateScore(player.getId(), 0);

        if (room.getPlayerCount() == room.getCapacity()) {
            roundService.createOrChangeRound(roomId);
            roundService.startRound(roomId);
        }
        roomSinkMap.get(roomId)
                .tryEmitNext(new GameDto<>(
                        ServerCode.GAME_DETAIL,
                        roomToDto(room)
                ));

        return room;
    }

    @Override
    public void removePlayer(String roomId, int playerId) throws RoomNotFoundException {
        Room room = getRoom(roomId);
        room.removePlayer(playerId);
    }

    @Override
    public Flux<GameDto> getRoomStream(String id) {

        return roomSinkMap.get(id).asFlux();
    }

    @Override
    public void broadcastRoomDetails(String roomId) throws RoomNotFoundException {

        RoomDto roomDto = roomToDto(getRoom(roomId));

        roomSinkMap.get(roomId).tryEmitNext(
                new GameDto<>(ServerCode.GAME_DETAIL, roomDto)
        );
    }


    @Override
    public void broadcastRound(String roomId) {
        RoundDto roundDto = roundService.roundToDto(
                roundService.getRound(roomId)
        );

        roomSinkMap.get(roomId).tryEmitNext(
                new GameDto<>(ServerCode.ROUND, roundDto)
        );
    }

    @Override
    public String selectWord(String roomId, int pos) {
        return roundService.selectWord(roomId, pos);
    }

    @Override
    public Room dtoToRoom(RoomDto roomDto) {

        if (roomDto.getCapacity() == null) {
            roomDto.setCapacity(4);
        }

        Room room = new Room(roomDto.getCapacity());
        room.setName(roomDto.getName());

//        return modelMapper.map(roomDto, Room.class);
        return room;
    }

    @Override
    public RoomDto roomToDto(Room room) {

        RoomDto dto = modelMapper.map(room, RoomDto.class);
        return dto;
    }
}
