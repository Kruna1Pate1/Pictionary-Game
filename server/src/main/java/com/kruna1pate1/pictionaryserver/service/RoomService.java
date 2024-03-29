package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.GameDto;
import com.kruna1pate1.pictionaryserver.dto.RoomDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.exception.RoomOutOfCapacityException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by KRUNAL on 18-05-2022
 */
public interface RoomService {

    Room getRoom(String id) throws RoomNotFoundException;

    List<Room> getAllRoom(String query);

    List<Room> getRoomStartsWith(String s);

    Room createRoom(String name, int capacity);

    Room removeRoom(String id) throws RoomNotFoundException;

    void removeAll();

    Room addPlayer(String roomId, Player player) throws RoomNotFoundException, RoomOutOfCapacityException;

    void removePlayer(String roomId, int playerId) throws RoomNotFoundException;

    Flux<GameDto> getRoomStream(String id);

    void broadcastRoomDetails(String roomId) throws RoomNotFoundException;

    void broadcastRound(String roomId);

    String selectWord(String roomId, int pos);

    Room dtoToRoom(RoomDto playerDto);

    RoomDto roomToDto(Room room);
}
