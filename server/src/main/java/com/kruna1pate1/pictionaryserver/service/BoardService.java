package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.BoardDto;
import com.kruna1pate1.pictionaryserver.model.DrawData;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 23-05-2022
 */
public interface BoardService {
    void createBoard(String roomId);

    Flux<DrawData> getBoard(String roomId);

    void sendDrawData(String roomId, DrawData drawData);

    void removeBoard(String roomId);
}
