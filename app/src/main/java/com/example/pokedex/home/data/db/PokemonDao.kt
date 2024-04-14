package com.example.pokedex.home.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    /*@Query("SELECT * from PokemonEntity")
    suspend fun getPokemonList(): Flow<List<PokemonEntity>>

    @Query("SELECT * from PokemonEntity WHERE id = :id")
    suspend fun getPokemonById(id: String): Flow<PokemonEntity>

    @Query("SELECT * from PokemonEntity WHERE id = :name")
    suspend fun getPokemonByName(name: String): Flow<PokemonEntity>*/

    @Query("SELECT * from PokemonEntity WHERE id = :pokemon OR name = :pokemon")
    suspend fun getPokemonByIdOrName(pokemon: String): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemon(pokemon: PokemonEntity)

}
