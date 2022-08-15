package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.RoundDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Round;

/**
 * Created by KRUNAL on 18-05-2022
 */
public interface RoundService {

    Round createOrChangeRound(String roomId) throws RoomNotFoundException;

    Round getRound(String roomId);

    void removeRound(String roomId);

    void removeAllRounds();

    void startRound(String roomId);

    String selectWord(String roomId, int pos);

    RoundDto roundToDto(Round round);
}
