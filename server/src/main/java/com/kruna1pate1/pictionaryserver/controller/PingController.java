package com.kruna1pate1.pictionaryserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by KRUNAL on 12-05-2022
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PingController {

    @MessageMapping("test.ping")
    public Mono<String> ping() {
        log.debug("Ping request received");
        return Mono.just("PONG");
    }

    @MessageMapping("test.echo")
    public Mono<String> echo(Mono<String> data) {
        return data
                .doOnNext(msg -> log.debug("{} received", msg))
                .map(msg -> {
                    DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT);
                    String time = dateFormat.format(new Date());
                    return time + " : " + msg;
                });
    }

}
