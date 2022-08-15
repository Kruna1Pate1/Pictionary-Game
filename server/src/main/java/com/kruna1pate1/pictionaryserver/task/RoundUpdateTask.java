package com.kruna1pate1.pictionaryserver.task;

import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Round;
import com.kruna1pate1.pictionaryserver.model.enums.RoundStatus;
import com.kruna1pate1.pictionaryserver.service.RoomService;
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
    private final RoomService roomService;
    private final String roomId;

    private boolean statusFlag;


    public RoundUpdateTask(String roomId, RoundService roundService, RoomService roomService) {
        this.timeRemain = 10;
        this.roundService = roundService;
        this.roomService = roomService;
        this.roomId = roomId;
        this.round = roundService.getRound(roomId);
        statusFlag = true;
    }

    @Override
    public void run() {

        round.setTimeRemain(timeRemain);

        if (statusFlag) {
            roomService.broadcastRound(roomId);
            statusFlag = false;
        }
//        log.debug(round.toString());

        switch (round.getStatus()) {

            case INITIAL -> {
                round.setStatus(RoundStatus.SELECTING_WORD);
                statusFlag = true;
            }

            case SELECTING_WORD -> {
                timeRemain--;
                if (timeRemain <=0) {
                    if (round.getWordGroup().getSelectedWord() == null) {
                        round.getWordGroup().setSelectedWord(0);
                    }
                    timeRemain = 90;
                    round.setStatus(RoundStatus.RUNNING);
                    statusFlag = true;
                }
            }
            case RUNNING -> {
                timeRemain--;
                if (timeRemain <= 0) {
                    timeRemain = 6;
                    round.setStatus(RoundStatus.ANSWER);
                    statusFlag = true;
                }
            }

            case ANSWER -> {
                timeRemain--;
//                roomService.broadcastScore(roomId);
                if (timeRemain <= 0) {
                    round.setStatus(RoundStatus.COMPLETE);
                    statusFlag = true;
                }
            }
            case COMPLETE -> {
                try {
                    roundService.createOrChangeRound(roomId);
                    this.round = roundService.getRound(roomId);
                    timeRemain = 15;
//                    statusFlag = true;
                } catch (RoomNotFoundException ignored) { }
            }
        }

    }
}
