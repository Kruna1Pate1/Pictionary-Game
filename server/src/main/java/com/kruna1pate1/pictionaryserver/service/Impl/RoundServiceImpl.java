package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.RoundDto;
import com.kruna1pate1.pictionaryserver.exception.RoomNotFoundException;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.model.Round;
import com.kruna1pate1.pictionaryserver.model.WordGroup;
import com.kruna1pate1.pictionaryserver.model.enums.PlayerStatus;
import com.kruna1pate1.pictionaryserver.model.enums.RoundStatus;
import com.kruna1pate1.pictionaryserver.service.RoomService;
import com.kruna1pate1.pictionaryserver.service.RoundService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import com.kruna1pate1.pictionaryserver.task.RoundUpdateTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by KRUNAL on 18-05-2022
 */
@Slf4j
@Service
public class RoundServiceImpl implements RoundService {

    private final ModelMapper modelMapper;
    private final Map<String, Round> rounds;
    private final List<String> demoWordList;
    private final RoomService roomService;
    private final ThreadPoolTaskScheduler taskScheduler;

    public RoundServiceImpl(ModelMapper modelMapper, RoomService roomService, ThreadPoolTaskScheduler taskScheduler) {
        this.modelMapper = modelMapper;
        this.roomService = roomService;
        this.taskScheduler = taskScheduler;
        this.rounds = new HashMap<>();
        this.demoWordList = List.of(
                "computer", "github", "discord", "pen", "india", "keyboard",
                "android studio", "battery", "language", "party", "volume"
        );
    }

    @Override
    public Round createOrChangeRound(String roomId) throws RoomNotFoundException {
        Round round = new Round();
        round.setWordGroup(new WordGroup(demoWordList));
        round.setStatus(RoundStatus.INITIAL);
        rounds.put(roomId, round);
        Room room = roomService.getRoom(roomId);
        room.setRound(round);
        round.setDrawer(room.changeDrawer());

        for (Player p: room.getPlayers()) {
            if (p == null) continue;
            if (p.equals(round.getDrawer())) {
                p.setStatus(PlayerStatus.DRAWING);
            } else {
                p.setStatus(PlayerStatus.GUESSING);
            }
        }

        return round;
    }

    @Override
    public Round getRound(String roomId) {

        return rounds.get(roomId);
    }

    @Override
    public void removeRound(String roomId) {

        rounds.remove(roomId);
    }

    @Override
    public void removeAllRounds() {

        rounds.clear();
    }

    @Override
    public void startRound(String roomId) {

        RoundUpdateTask task = new RoundUpdateTask(roomId, this, roomService);
        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task, 1000L);
        rounds.get(roomId).setFuture(future);
    }

    @Override
    public String selectWord(String roomId, int pos) {

        Round round = rounds.get(roomId);
        return round.getWordGroup().setSelectedWord(pos);
//        round.setStatus(RoundStatus.RUNNING);

    }

    @Override
    public RoundDto roundToDto(Round round) {
        return modelMapper.map(round, RoundDto.class);
    }

}
