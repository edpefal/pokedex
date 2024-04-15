package com.example.pokedex.team.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTeamDao {
    @Query("SELECT * from PokemonTeamMemberEntity")
    fun getPokemonTeam(): Flow<List<PokemonTeamMemberEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM PokemonTeamMemberEntity WHERE id = :id)")
    suspend fun isPokemonAlreadyInTeam(id: String): Boolean

    @Query("SELECT COUNT(id) FROM PokemonTeamMemberEntity")
    suspend fun getTeamCount(): Int

    @Delete
    suspend fun deleteTeamMember(pokemonTeamMemberEntity: PokemonTeamMemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonTeamMember(pokemon: PokemonTeamMemberEntity)
}