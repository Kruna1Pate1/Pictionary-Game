package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.BoardDto;
import com.kruna1pate1.pictionaryserver.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KRUNAL on 22-05-2022
 */
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final Map<String, Sinks.Many<BoardDto>> boardSinkMap;

    public BoardServiceImpl() {
        this.boardSinkMap = new HashMap<>();
    }

    @Override
    public void createBoard(String roomId) {

        boardSinkMap.put(roomId, Sinks.many().multicast().directBestEffort());
    }

    @Override
    public Flux<BoardDto> getBoard(String roomId) {

        return boardSinkMap.get(roomId).asFlux();
    }

    @Override
    public void sendBoard(String roomId, BoardDto board) {

        boardSinkMap.get(roomId).tryEmitNext(board);
    }

    @Override
    public void removeBoard(String roomId) {

        boardSinkMap.remove(roomId);
    }
}
