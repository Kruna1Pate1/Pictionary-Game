package com.kruna1pate1.pictionaryapp.domain.usecase.room

import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.domain.repository.RoomRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
){

    suspend operator fun invoke(createRoom: CreateRoom): Resource<Room> {
        return roomRepository.createRoom(createRoom);
    }
}