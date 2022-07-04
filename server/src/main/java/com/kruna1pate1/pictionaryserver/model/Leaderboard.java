package com.kruna1pate1.pictionaryserver.model;

import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KRUNAL on 21-05-2022
 */
@Data
public class Leaderboard {

    private Map<Integer, Integer> scores = new HashMap<>();

    public Map.Entry<Integer, Integer> getHighestScore() {
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue());
    }

    public int addOrUpdateScore(int playerId, int score) {
        int score1 = scores.getOrDefault(playerId, 0) + score;
        scores.put(playerId, score1);

        return score1;
    }

    public int getScore(int playerId) {
        return scores.getOrDefault(playerId, -1);
    }

    public boolean resetScore(int playerId) {
        if (scores.containsKey(playerId)) {
            scores.replace(playerId, 0);
            return true;
        }
        return false;
    }

    public void resetAll() {
        scores.replaceAll((k, v) -> 0);
    }

}
