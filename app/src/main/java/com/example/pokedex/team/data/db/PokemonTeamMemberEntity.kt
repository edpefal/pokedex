package com.example.pokedex.team.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonTeamMemberEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String
)