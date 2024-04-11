package com.example.pokedex.home.data.network

import com.example.pokedex.home.data.model.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(private val homeClient: HomeClient) {

    suspend fun getPokemon(pokemonNameOrNumber: String): ApiResult<PokemonResponse> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(homeClient.getPokemon(pokemonNameOrNumber))
            }catch (exception: Exception){
                ApiResult.Error(exception)
            }
        }
    }

}