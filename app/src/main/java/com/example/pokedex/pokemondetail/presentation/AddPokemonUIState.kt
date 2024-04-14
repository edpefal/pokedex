package com.example.pokedex.pokemondetail.presentation

sealed class AddPokemonUIState {
    data object MaxNumber: AddPokemonUIState()
    data object Success: AddPokemonUIState()
    data object AlreadyExist: AddPokemonUIState()
    data object Empty: AddPokemonUIState()

}