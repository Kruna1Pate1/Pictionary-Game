package com.kruna1pate1.pictionaryserver.model;

import com.kruna1pate1.pictionaryserver.model.enums.PlayerStatus;
import lombok.Data;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class Player {

    private Integer id;

    private String name;

    private PlayerStatus status;

}
