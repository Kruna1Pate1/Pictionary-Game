package com.kruna1pate1.pictionaryserver.model;

import com.kruna1pate1.pictionaryserver.model.enums.CanvasAction;
import com.kruna1pate1.pictionaryserver.model.enums.Color;
import com.kruna1pate1.pictionaryserver.model.enums.MotionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
@AllArgsConstructor
public class DrawData {

    private Float x;

    private Float y;

    private Integer me;

    private CanvasAction action;

    private Integer color;
}
