package com.example.pokedex.pokemondetail.domain

import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.pokemondetail.data.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowPokemonInfoUseCase @Inject constructor(private val pokemonDetailRepository: PokemonDetailRepository) {

    operator fun invoke (pokemonNameOrNumber: String): Flow<PokemonDetailModel> {
        return pokemonDetailRepository.getPokemonToShow(pokemonNameOrNumber)
    }
}