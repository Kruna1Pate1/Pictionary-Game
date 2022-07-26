package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.exception.PlayerNotFoundException;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.exception.RoomOutOfCapacityException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by KRUNAL on 21-05-2022
 */
@Slf4j
@Service
public class GameService {

    private final RoomService roomService;
    private final PlayerServiceImpl playerService;
    private final LeaderboardService leaderboardService;
    private final ChatService chatService;
    private final RoundService roundService;
    private final BoardService boardService;

    public GameService(RoomService roomService, PlayerServiceImpl playerService, LeaderboardService leaderboardService, ChatService chatService, RoundService roundService, BoardService boardService) {
        this.roomService = roomService;
        this.playerService = playerService;
        this.leaderboardService = leaderboardService;
        this.chatService = chatService;
        this.roundService = roundService;
        this.boardService = boardService;
    }

    public Room createRoom(String name, int capacity) {

        Room room1 = roomService.createRoom(name, capacity);

        room1.setLeaderboard(leaderboardService.createLeaderboard(room1.getId()));
        try {
            room1.setRound(roundService.createOrChangeRound(room1.getId()));
            chatService.createChat(room1.getId());
            boardService.createBoard(room1.getId());

        } catch (RoomNotFoundException ignored) {
        }

        return room1;
    }

    public Room joinRoom(String roomId, int playerId) throws PlayerNotFoundException, RoomNotFoundException, RoomOutOfCapacityException {

        Player player = playerService.getPlayer(playerId);
        Room room = roomService.addPlayer(roomId, player);

        return room;
    }

    public void leaveRoom(String roomId, int playerId) throws RoomNotFoundException {

        roomService.removePlayer(roomId, playerId);
    }
}
