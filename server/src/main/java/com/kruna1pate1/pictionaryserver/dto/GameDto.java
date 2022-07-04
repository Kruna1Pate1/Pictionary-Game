package com.kruna1pate1.pictionaryserver.dto;

import com.kruna1pate1.pictionaryserver.model.enums.ServerCode;

/**
 * Created by KRUNAL on 30-06-2022
 */
public record GameDto<T>(
        ServerCode code,
        T data
) {
}
