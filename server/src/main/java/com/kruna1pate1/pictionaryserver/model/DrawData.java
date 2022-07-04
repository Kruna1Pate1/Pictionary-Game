package com.kruna1pate1.pictionaryserver.model;

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

    private Float x1;

    private Float y1;

    private Float x2;

    private Float y2;

    private MotionEvent motionEvent;

    private Color color;

}
