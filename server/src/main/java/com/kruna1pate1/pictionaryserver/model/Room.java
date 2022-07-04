package com.kruna1pate1.pictionaryserver.model;

import com.kruna1pate1.pictionaryserver.model.enums.GameStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class Room {

    private String id;

    private String name;

    @Setter(AccessLevel.NONE)
    private int capacity;

    private Player[] players;

    @Setter(AccessLevel.NONE)
    private int drawer;

    private GameStatus status;

    private Round round;

    private Leaderboard leaderboard;
    @Setter(AccessLevel.NONE)
    private int playerCount;

    public Room() {
        this(5);
    }

    public Room(int capacity) {
        this.capacity = capacity;
        this.players = new Player[capacity];
        this.playerCount = 0;
        this.drawer = -1;
    }

    public boolean addPlayer(Player player) {

        if (playerCount != capacity) {
            for (int i = 0; i < capacity; i++) {
                if (players[i] == null) {
                    players[i] = player;
                    playerCount++;

                    return true;
                }
            }
        }
        return false;
    }

    public void removePlayer(int playerId) {
        if (playerCount != 0) {
            for (int i = 0; i < capacity; i++) {
                if (players[i].getId() == playerId) {
                    players[i] = null;
                    playerCount--;

                    return;
                }
            }
        }
    }

    public Player changeDrawer() {
        if (players == null || playerCount == 0) return null;
        do {
            drawer = (++drawer % capacity);
        } while (players[drawer] == null);

        return players[drawer];
    }

    public Player getDrawer() {
        return players[drawer];
    }

}
