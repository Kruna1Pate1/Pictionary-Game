package com.kruna1pate1.pictionaryapp.domain.usecase.room

import android.util.Log
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.domain.repository.RoomRepository
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import com.kruna1pate1.pictionaryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    operator fun invoke(query: String): Flow<Resource<Room>> {

        return roomRepository.getAllRooms(query)
    }
}