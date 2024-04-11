package com.example.pokedex.home.data.model

import com.google.gson.annotations.SerializedName


data class PokemonResponse(
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("sprites")
    var sprites: Sprites? = null,
    @SerializedName("stats")
    var stats: ArrayList<Stats>? = null,
    @SerializedName("types")
    var types: ArrayList<Types>? = null,
    @SerializedName("weight")
    var weight: Int? = null,

    @SerializedName("image")
    val image: String? =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png"

)


