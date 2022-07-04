package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.LeaderboardDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Leaderboard;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 22-05-2022
 */
public interface LeaderboardService {
    Leaderboard createLeaderboard(String roomId);

    Flux<LeaderboardDto> getLeaderboard(String roomId);

    void removeLeaderboard(String roomId) throws RoomNotFoundException;
}
