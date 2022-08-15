package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Leaderboard;
import com.kruna1pate1.pictionaryserver.service.LeaderboardService;
import com.kruna1pate1.pictionaryserver.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by KRUNAL on 21-05-2022
 */
@Slf4j
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final RoomService roomService;


    public LeaderboardServiceImpl(@Lazy RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public Leaderboard createLeaderboard(String roomId) {

        return new Leaderboard();
    }

    @Override
    public Leaderboard getLeaderboard(String roomId) {
        return roomService.getRoom(roomId).getLeaderboard();
    }

    @Override
    public void removeLeaderboard(String roomId) throws RoomNotFoundException {
        roomService.getRoom(roomId).setLeaderboard(null);
    }
}
