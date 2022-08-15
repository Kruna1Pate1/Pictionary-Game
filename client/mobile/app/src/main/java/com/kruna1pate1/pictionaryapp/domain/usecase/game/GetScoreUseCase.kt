package com.kruna1pate1.pictionaryapp.domain.usecase.game

import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.Score
import com.kruna1pate1.pictionaryapp.domain.repository.GameRepository
import com.kruna1pate1.pictionaryapp.util.Resource
import javax.inject.Inject

class GetScoreUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend operator fun invoke(roomId: String): Resource<Score> {

        return try {

            val players: List<Player> = gameRepository.getPlayers(roomId).data!!
            val scores: Map<Int, Int> = gameRepository.getScores(roomId).data!!

            val s = Score(
                buildMap {
                    players.forEach { p ->
                        scores[p.id]?.let {
                            put(p, it)
                        }
                    }
                }
            )
            Resource.Success(s)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Can't get score")
        }
    }
}