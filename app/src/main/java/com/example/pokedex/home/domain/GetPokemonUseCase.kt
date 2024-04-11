package com.example.pokedex.home.domain

import com.example.pokedex.home.data.repository.HomeRepository
import com.example.pokedex.home.presentation.HomeUIState
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke (pokemonNameOrNumber: String): HomeUIState {
        return homeRepository.getPokemon(pokemonNameOrNumber)
    }
}