package com.kruna1pate1.pictionaryapp.di

import android.util.Log
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.data.preference.Preference
import com.kruna1pate1.pictionaryapp.data.remote.ChatApi
import com.kruna1pate1.pictionaryapp.data.remote.GameApi
import com.kruna1pate1.pictionaryapp.data.remote.PlayerApi
import com.kruna1pate1.pictionaryapp.data.remote.RoomApi
import com.kruna1pate1.pictionaryapp.data.repository.ChatRepositoryImpl
import com.kruna1pate1.pictionaryapp.data.repository.GameRepositoryImpl
import com.kruna1pate1.pictionaryapp.data.repository.PlayerRepositoryImpl
import com.kruna1pate1.pictionaryapp.data.repository.RoomRepositoryImpl
import com.kruna1pate1.pictionaryapp.domain.repository.ChatRepository
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.domain.repository.PlayerRepository
import com.kruna1pate1.pictionaryapp.domain.repository.RoomRepository
import com.kruna1pate1.pictionaryapp.util.Constants
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.core.RSocketConnector
import io.rsocket.kotlin.core.WellKnownMimeType
import io.rsocket.kotlin.payload.PayloadMimeType
import io.rsocket.kotlin.transport.ktor.tcp.TcpClientTransport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // RSocket connection for all repository tasks
    @Singleton
    @Provides
    fun providesRSocket(): RSocket {

        val transport = TcpClientTransport(Constants.SERVER_IP, Constants.SERVER_PORT)
        val connector = RSocketConnector {
            connectionConfig {
                payloadMimeType = PayloadMimeType(
                    data = WellKnownMimeType.ApplicationJson,
                    metadata = WellKnownMimeType.MessageRSocketRouting
                )
            }
        }

        return CoroutineScope(Dispatchers.IO).run {
            Log.d(TAG, "providesRSocket: in coroutine")
            val deferred = async {
                Log.d(TAG, "providesRSocket: in async")
                connector.connect(transport)
            }

            runBlocking {
                Log.d(TAG, "providesRSocket: run blocking")
                return@runBlocking deferred.await()
            }
        }

    }

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesPlayerApi(rSocket: RSocket, gson: Gson): PlayerApi = PlayerApi(rSocket, gson)


    @Singleton
    @Provides
    fun providesPlayerRepository(
        playerApi: PlayerApi,
        preference: Preference,
        gson: Gson
    ): PlayerRepository =
        PlayerRepositoryImpl(playerApi, preference, gson)


    @Singleton
    @Provides
    fun providesRoomApi(rSocket: RSocket, gson: Gson): RoomApi = RoomApi(rSocket, gson)

    @Singleton
    @Provides
    fun providesRoomRepository(
        roomApi: RoomApi,
        gson: Gson
    ): RoomRepository =
        RoomRepositoryImpl(roomApi, gson)


    @Singleton
    @Provides
    fun providesGameApi(rSocket: RSocket, gson: Gson): GameApi = GameApi(rSocket, gson)

    @Singleton
    @Provides
    fun providesGameRepository(
        gameApi: GameApi,
        gson: Gson
    ): GameRepository =
        GameRepositoryImpl(gameApi, gson)


    @Singleton
    @Provides
    fun providesChatApi(rSocket: RSocket, gson: Gson): ChatApi = ChatApi(rSocket, gson)

    @Singleton
    @Provides
    fun providesChatRepository(
        chatApi: ChatApi,
        gson: Gson
    ): ChatRepository =
        ChatRepositoryImpl(chatApi, gson)


}