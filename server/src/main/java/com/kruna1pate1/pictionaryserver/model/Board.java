package com.kruna1pate1.pictionaryserver.model;

import com.kruna1pate1.pictionaryserver.model.enums.BoardAction;
import lombok.Data;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class Board {

    private BoardAction action;

    private DrawData data;
}
