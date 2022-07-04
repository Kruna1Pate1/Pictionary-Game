package com.kruna1pate1.pictionaryserver.service;

import com.kruna1pate1.pictionaryserver.dto.MessageDto;
import com.kruna1pate1.pictionaryserver.exception.NotFoundException;
import com.kruna1pate1.pictionaryserver.model.Message;

import java.util.List;

/**
 * Created by KRUNAL on 18-05-2022
 */
public interface MessageService {

    Message getMessageById(long id) throws NotFoundException;

    List<Message> getAllMessages();

    void addMessage(Message message);

    Message removeMessage(long id) throws NotFoundException;

    void removeAll();

    Message updateMessage(long id, Message message) throws NotFoundException;

    Message dtoToMessage(MessageDto playerDto);


    MessageDto messageToDto(Message player);
}
