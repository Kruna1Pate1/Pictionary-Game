package com.kruna1pate1.pictionaryserver.task;

import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Round;
import com.kruna1pate1.pictionaryserver.model.enums.RoundStatus;
import com.kruna1pate1.pictionaryserver.service.RoundService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by KRUNAL on 24-05-2022
 */
@Slf4j
public class RoundUpdateTask implements Runnable {

    private Round round;
    private int timeRemain;
    private final RoundService roundService;
    private final String roomId;


    public RoundUpdateTask(String roomId, RoundService roundService) {
        this.timeRemain = 10;
        this.roundService = roundService;
        this.roomId = roomId;
        this.round = roundService.getRound(roomId);
    }

    @Override
    public void run() {

        round.setTimeRemain(timeRemain);

        log.debug(round.toString());

        switch (round.getStatus()) {

            case INITIAL -> {
                round.setStatus(RoundStatus.SELECTING_WORD);
            }

            case SELECTING_WORD -> {
                timeRemain--;
                if (timeRemain <=0) {
                    if (round.getWordGroup().getSelectedWord() == null) {
                        round.getWordGroup().setSelectedWord(0);
                    }
                    timeRemain = 90;
                    round.setStatus(RoundStatus.RUNNING);
                }
            }
            case RUNNING -> {
                timeRemain--;
                if (timeRemain <= 0) {
                    timeRemain = 6;
                    round.setStatus(RoundStatus.ANSWER);
                }
            }

            case ANSWER -> {
                timeRemain--;
                if (timeRemain <= 0) {
                    round.setStatus(RoundStatus.COMPLETE);
                }
            }
            case COMPLETE -> {
                try {
                    roundService.createOrChangeRound(roomId);
                    this.round = roundService.getRound(roomId);
                    timeRemain = 15;
                } catch (RoomNotFoundException ignored) { }
            }
        }

    }
}
