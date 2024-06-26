package com.example.pokedex.pokemondetail.domain

import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.pokemondetail.presentation.AddPokemonUIState
import com.example.pokedex.team.data.PokemonTeamRepository
import javax.inject.Inject

const val MAX_NUMBER_OF_POKEMONS = 6

class AddPokemonTeamMemberUseCase @Inject constructor(
    private val pokemonTeamRepository: PokemonTeamRepository
) {

    suspend operator fun invoke(pokemonDetailModel: PokemonDetailModel): AddPokemonResult {
        val exist = pokemonTeamRepository.pokemonAlreadyInTeam(pokemonDetailModel.id)
        return if (exist) {
            AddPokemonResult.AlreadyAdded
        } else if (pokemonTeamRepository.getTeamCount() < MAX_NUMBER_OF_POKEMONS) {
            pokemonTeamRepository.addPokemonTeamMember(pokemonDetailModel)
            AddPokemonResult.Success
        } else {
            AddPokemonResult.Failure
        }
    }
}