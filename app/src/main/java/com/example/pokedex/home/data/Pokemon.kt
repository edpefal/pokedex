package com.example.pokedex.home.data

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") val name: String? = "Ditto",
    @SerializedName("image") val image: String? = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png"
)