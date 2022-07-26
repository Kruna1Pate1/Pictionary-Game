package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.model.DrawData;
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

    private final Map<String, Sinks.Many<DrawData>> boardSinkMap;

    public BoardServiceImpl() {
        this.boardSinkMap = new HashMap<>();
    }

    @Override
    public void createBoard(String roomId) {

        boardSinkMap.put(roomId, Sinks.many().multicast().directBestEffort());
    }

    @Override
    public Flux<DrawData> getBoard(String roomId) {

        return boardSinkMap.get(roomId).asFlux();
    }

    @Override
    public void sendDrawData(String roomId, DrawData drawData) {
        log.debug(drawData.toString());
        boardSinkMap.get(roomId).tryEmitNext(drawData);
    }

    @Override
    public void removeBoard(String roomId) {

        boardSinkMap.remove(roomId);
    }
}
