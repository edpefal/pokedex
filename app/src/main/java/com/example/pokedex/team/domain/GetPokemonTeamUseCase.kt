package com.example.pokedex.team.domain

import com.example.pokedex.team.data.PokemonTeamRepository
import com.example.pokedex.team.presentation.PokemonTeamMemberModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonTeamUseCase @Inject constructor(private val pokemonTeamRepository: PokemonTeamRepository) {

    operator fun invoke(): Flow<List<PokemonTeamMemberModel>> {
        return pokemonTeamRepository.getTeam()
    }

}