package com.example.pokedex.home.presentation

import com.example.pokedex.home.presentation.model.PokemonModel

sealed class HomeUIState{
    object Empty: HomeUIState()
    object Loading: HomeUIState()
    class Success(val pokemonModel: PokemonModel): HomeUIState()
    object Error: HomeUIState()

}