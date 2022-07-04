package com.kruna1pate1.pictionaryserver.model;

import lombok.Data;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class Message {

    private Player sender;

    private String body;

    private String time;

    public Message() {
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
        this.time = dateFormat.format(new Date());
    }
}
