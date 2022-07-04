package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.LeaderboardDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Leaderboard;
import com.kruna1pate1.pictionaryserver.service.LeaderboardService;
import com.kruna1pate1.pictionaryserver.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KRUNAL on 21-05-2022
 */
@Slf4j
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final RoomService roomService;
    private final Map<String, Sinks.Many<LeaderboardDto>> leaderboardSinkMap;

    public LeaderboardServiceImpl(RoomService roomService) {
        this.roomService = roomService;
        this.leaderboardSinkMap = new HashMap<>();
    }

    @Override
    public Leaderboard createLeaderboard(String roomId) {

        leaderboardSinkMap.put(roomId, Sinks.many().multicast().directBestEffort());
        return new Leaderboard();
    }

    @Override
    public Flux<LeaderboardDto> getLeaderboard(String roomId) {

        return leaderboardSinkMap.get(roomId).asFlux();
    }

    @Override
    public void removeLeaderboard(String roomId) throws RoomNotFoundException {
        roomService.getRoom(roomId).setLeaderboard(null);
        leaderboardSinkMap.remove(roomId);
    }
}
