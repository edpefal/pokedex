package com.example.pokedex.home.data.network

import com.example.pokedex.home.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeClient {
    @GET("api/v2/pokemon/{pokemon}")
    suspend fun getPokemon(@Path("pokemon") pokemon: String): PokemonResponse
}