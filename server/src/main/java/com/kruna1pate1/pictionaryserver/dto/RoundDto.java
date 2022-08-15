package com.kruna1pate1.pictionaryserver.dto;

import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.WordGroup;
import com.kruna1pate1.pictionaryserver.model.enums.RoundStatus;
import lombok.Data;

/**
 * Created by KRUNAL on 07-08-2022
 */

@Data
public class RoundDto {

    private WordGroup wordGroup;

    private Player drawer;

    private RoundStatus status;

    private int timeRemain;
}
