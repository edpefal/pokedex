package com.example.pokedex.pokemondetail.data

import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats

data class PokemonDetailModel(
    val id: String,
    var height: String,
    var name: String,
    var sprites: Sprites,
    var stats: List<Stats>,
    var types: List<String>,
    var weight: String
)