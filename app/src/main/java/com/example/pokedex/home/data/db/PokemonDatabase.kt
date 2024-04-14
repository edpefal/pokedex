package com.example.pokedex.home.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.pokemondetail.data.PokemonDetailDao
import com.example.pokedex.team.data.db.PokemonTeamDao
import com.example.pokedex.team.data.db.PokemonTeamMemberEntity

@Database(entities = [PokemonEntity::class, PokemonTeamMemberEntity::class], version = 1)

@TypeConverters(value = [PokemonConverter::class])

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonDetailDao(): PokemonDetailDao
    abstract fun pokemonTeamDao(): PokemonTeamDao
}