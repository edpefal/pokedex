package com.example.pokedex.home.presentation

import com.example.pokedex.home.presentation.model.PokemonModel

sealed class HomeUIState{
    data object Empty: HomeUIState()
    data object Loading: HomeUIState()
    class Success(val pokemonModel: PokemonModel): HomeUIState()
    data object Error: HomeUIState()

}