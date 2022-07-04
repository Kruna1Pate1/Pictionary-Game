package com.kruna1pate1.pictionaryserver.dto;

import com.kruna1pate1.pictionaryserver.model.enums.PlayerStatus;
import lombok.Data;

/**
 * Created by KRUNAL on 19-05-2022
 */
@Data
public class PlayerDto {

    private int id;

    private String name;

    private PlayerStatus status;
}
