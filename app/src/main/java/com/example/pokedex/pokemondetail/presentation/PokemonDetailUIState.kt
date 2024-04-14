package com.example.pokedex.pokemondetail.presentation

import com.example.pokedex.pokemondetail.data.PokemonDetailModel

sealed class PokemonDetailUIState {
    class Success(val pokemonDetailModel: PokemonDetailModel): PokemonDetailUIState()
    data object Error: PokemonDetailUIState()
    data object Loading: PokemonDetailUIState()
}