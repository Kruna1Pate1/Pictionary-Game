package com.kruna1pate1.pictionaryserver.service.Impl;

import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.model.Player;
import com.kruna1pate1.pictionaryserver.model.Room;
import com.kruna1pate1.pictionaryserver.model.Round;
import com.kruna1pate1.pictionaryserver.model.enums.PlayerStatus;
import com.kruna1pate1.pictionaryserver.service.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KRUNAL on 18-05-2022
 */
@Service
public class ChatServiceImpl implements ChatService {


    private final RoomService roomService;
    private final Map<String, Sinks.Many<MessageDto>> chatSinkMap;

    public ChatServiceImpl(RoomService roomService) {
        this.roomService = roomService;
        this.chatSinkMap = new HashMap<>();
    }

    @Override
    public Flux<MessageDto> getChat(String roomId) {

        return chatSinkMap.get(roomId).asFlux();
    }

    @Override
    public void createChat(String roomId) {

        chatSinkMap.put(roomId, Sinks.many().multicast().directBestEffort());
    }

    @Override
    public void removeChat(String roomId) {

        chatSinkMap.remove(roomId);
    }

    @Override
    public void sendMessage(String roomId, MessageDto messageDto) {

        Room room = roomService.getRoom(roomId);
        Round round = room.getRound();
        String ans = round.getWordGroup().getSelectedWord();
        if (ans != null && ans.equalsIgnoreCase(messageDto.getBody())) {

            messageDto.setBody("Guessed correct!");
            room.getLeaderboard().addOrUpdateScore(messageDto.getPlayer().getId(), round.getTimeRemain());
            for (Player p: room.getPlayers()) {
                if (p == null) continue;
                if (p.getId() == messageDto.getPlayer().getId()) {
                    p.setStatus(PlayerStatus.GUESSED);
                }
            }
        }
        chatSinkMap.get(roomId).tryEmitNext(messageDto);
    }

}
