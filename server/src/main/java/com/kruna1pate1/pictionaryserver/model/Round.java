package com.kruna1pate1.pictionaryserver.model;

import com.kruna1pate1.pictionaryserver.model.enums.RoundStatus;
import lombok.Data;
import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by KRUNAL on 14-05-2022
 */
@Data
public class Round {

    private WordGroup wordGroup;

    private Player drawer;

    private RoundStatus status;

    private int timeRemain;

    private ScheduledFuture<?> future;

}
