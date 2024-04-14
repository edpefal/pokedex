package com.example.pokedex.pokemondetail.domain

import com.example.pokedex.pokemondetail.data.PokemonDetailRepository
import com.example.pokedex.pokemondetail.presentation.PokemonDetailUIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowPokemonInfoUseCase @Inject constructor(private val pokemonDetailRepository: PokemonDetailRepository) {

    operator fun invoke (pokemonNameOrNumber: String): Flow<PokemonDetailUIState> {
        return pokemonDetailRepository.getPokemonToShow(pokemonNameOrNumber)
    }
}