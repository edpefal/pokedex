package com.example.pokedex.home.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.home.data.model.Types

@Entity
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    var height: Int,
    var name: String,
    var sprites: Sprites,
    var stats: List<Stats>,
    var types: List<Types>,
    var weight: Int
)