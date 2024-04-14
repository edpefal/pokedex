package com.example.pokedex.team.domain

import com.example.pokedex.team.data.PokemonTeamRepository
import com.example.pokedex.team.presentation.PokemonTeamMemberModel
import javax.inject.Inject

class DeletePokemonTeamMemberUseCase @Inject constructor(private val pokemonTeamRepository: PokemonTeamRepository) {

    suspend operator fun invoke(pokemonTeamMemberModel: PokemonTeamMemberModel) {
        pokemonTeamRepository.deleteTeamMember(pokemonTeamMemberModel)
    }
}