package com.kruna1pate1.pictionaryserver.controller;

import com.kruna1pate1.pictionaryserver.dto.LeaderboardDto;
import com.kruna1pate1.pictionaryserver.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * Created by KRUNAL on 22-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @MessageMapping("room.{id}.leaderboard")
    public Flux<LeaderboardDto> getLeaderboard(@DestinationVariable("id") String id) {

        return leaderboardService.getLeaderboard(id);
    }
}
