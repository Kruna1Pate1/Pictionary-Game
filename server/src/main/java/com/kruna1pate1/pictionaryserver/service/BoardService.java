package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.BoardDto;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 23-05-2022
 */
public interface BoardService {
    void createBoard(String roomId);

    Flux<BoardDto> getBoard(String roomId);

    void sendBoard(String roomId, BoardDto board);

    void removeBoard(String roomId);
}
