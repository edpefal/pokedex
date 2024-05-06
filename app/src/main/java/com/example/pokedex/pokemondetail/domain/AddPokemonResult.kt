package com.example.pokedex.pokemondetail.domain

sealed class AddPokemonResult {
    data object Success : AddPokemonResult()
    data object Failure : AddPokemonResult()
    data object AlreadyAdded : AddPokemonResult()
}