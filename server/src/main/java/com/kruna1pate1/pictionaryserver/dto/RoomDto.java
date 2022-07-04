package com.kruna1pate1.pictionaryserver.dto;

import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Round;
import com.kruna1pate1.pictionaryserver.model.enums.GameStatus;
import com.kruna1pate1.pictionaryserver.model.enums.ServerCode;
import lombok.Data;

import java.util.Map;

/**
 * Created by KRUNAL on 20-05-2022
 */
@Data
public class RoomDto {

    private String roomId;

//    private ServerCode responseCode;

    private String name;

    private Integer capacity;

    private PlayerDto[] players;

    private GameStatus status;

//    private Round round;
    private Integer playerCount;

}
