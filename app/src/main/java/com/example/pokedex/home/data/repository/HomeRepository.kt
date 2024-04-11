package com.example.pokedex.home.data.repository

import com.example.pokedex.home.data.network.ApiResult
import com.example.pokedex.home.data.network.HomeService
import com.example.pokedex.home.presentation.HomeUIState
import com.example.pokedex.home.presentation.model.PokemonModel
import javax.inject.Inject

class HomeRepository @Inject constructor(private val homeService: HomeService) {

    suspend fun getPokemon(pokemonNameOrNumber: String): HomeUIState {
        return when (val result = homeService.getPokemon(pokemonNameOrNumber)) {
            is ApiResult.Error -> HomeUIState.Error
            is ApiResult.Success -> HomeUIState.Success(
                PokemonModel(
                    result.data.name.orEmpty(),
                    result.data.sprites?.frontDefault ?: ""
                )
            )
        }
    }
}