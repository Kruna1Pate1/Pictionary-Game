package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.RoomDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.exception.RoomOutOfCapacityException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.model.enums.ServerCode;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by KRUNAL on 18-05-2022
 */
public interface RoomService {

    Room getRoom(String id) throws RoomNotFoundException;

    List<Room> getAllRoom();

    List<Room> getRoomStartsWith(String s);

    Room createRoom(Room room);

    Room removeRoom(String id) throws RoomNotFoundException;

    void removeAll();

    Room addPlayer(String roomId, Player player) throws RoomNotFoundException, RoomOutOfCapacityException;

    void removePlayer(String roomId, int playerId) throws RoomNotFoundException;

    Flux<GameDto> getRoomStream(String id);

    void broadcastRoomDetails(String roomId, RoomDto roomDto);

    void broadcastScores(String roomId);

    void broadcastRoomPlayers(String roomId);

    void broadcastAnswer(String roomId);

    void broadcastRound(String roomId);

    void broadcastHint(String roomId);

    String selectWord(String roomId, int pos);

    Room dtoToRoom(RoomDto playerDto);

    RoomDto roomToDto(Room room, ServerCode code);
}
