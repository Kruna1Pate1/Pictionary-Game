package com.kruna1pate1.pictionaryserver.dto;

import lombok.Data;

/**
 * Created by KRUNAL on 20-05-2022
 */
@Data
public class MessageDto {

    private PlayerDto sender;

    private String body;

    private String time;
}
