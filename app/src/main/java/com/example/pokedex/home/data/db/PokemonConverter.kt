package com.example.pokedex.home.data.db

import androidx.room.TypeConverter
import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.home.data.model.Types
import com.google.gson.Gson

class PokemonConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromStringToSprites(serialized: String): Sprites {
        return gson.fromJson(serialized, Sprites::class.java)
    }

    @TypeConverter
    fun spritesToString(entity: Sprites): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromStringToPokemonEntity(serialized: String): PokemonEntity {
        return gson.fromJson(serialized, PokemonEntity::class.java)
    }

    @TypeConverter
    fun pokemonEntityToString(entity: PokemonEntity): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromStringToStatsList(serialized: String)=  gson.fromJson(serialized, Array<Stats>::class.java).toList()

    @TypeConverter
    fun statsListToString(entity: List<Stats>): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun fromStringToTypesList(serialized: String)=  gson.fromJson(serialized, Array<Types>::class.java).toList()

    @TypeConverter
    fun typesListToString(entity: List<Types>): String {
        return gson.toJson(entity)
    }
}