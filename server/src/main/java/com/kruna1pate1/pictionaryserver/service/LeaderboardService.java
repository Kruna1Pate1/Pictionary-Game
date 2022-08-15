package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Leaderboard;

/**
 * Created by KRUNAL on 22-05-2022
 */
public interface LeaderboardService {
    Leaderboard createLeaderboard(String roomId);

    Leaderboard getLeaderboard(String roomId);

    void removeLeaderboard(String roomId) throws RoomNotFoundException;

}
