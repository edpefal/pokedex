package com.example.pokedex.pokemondetail.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.team.data.db.PokemonTeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDetailDao {

    @Query("SELECT * from PokemonEntity WHERE id = :id")
    fun getPokemonById(id: String): Flow<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonTeamMember(pokemon: PokemonTeamMemberEntity)

}