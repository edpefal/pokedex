package com.example.pokedex.team.data

import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.team.data.db.PokemonTeamDao
import com.example.pokedex.team.data.db.PokemonTeamMemberEntity
import com.example.pokedex.team.presentation.PokemonTeamMemberModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonTeamRepository @Inject constructor(private val teamDao: PokemonTeamDao) {

    fun getTeam(): Flow<List<PokemonTeamMemberModel>> {
        return teamDao.getPokemonTeam().map {
            it.map { entity->
                entity.toPokemonTeamMemberModel()
            }
        }
    }

    suspend fun pokemonAlreadyInTeam(pokemonId: String): Boolean {
        return teamDao.isPokemonAlreadyInTeam(pokemonId)
    }

    suspend fun getTeamCount(): Int {
        return teamDao.getTeamCount()
    }

    suspend fun deleteTeamMember(pokemonTeamMemberModel: PokemonTeamMemberModel) {
        teamDao.deleteTeamMember(pokemonTeamMemberModel.toPokemonTeamMemberEntity())
    }

    suspend fun addPokemonTeamMember(pokemonDetailModel: PokemonDetailModel) {
        teamDao.addPokemonTeamMember(pokemonDetailModel.toPokemonTemMemberEntity())
    }

    private fun PokemonDetailModel.toPokemonTemMemberEntity(): PokemonTeamMemberEntity {
        return PokemonTeamMemberEntity(
            this.id.toInt(),
            this.name,
            this.sprites.frontDefault.orEmpty()
        )
    }


    fun PokemonTeamMemberEntity.toPokemonTeamMemberModel(): PokemonTeamMemberModel {
        return PokemonTeamMemberModel(this.id, this.name, this.image)
    }

    fun PokemonTeamMemberModel.toPokemonTeamMemberEntity(): PokemonTeamMemberEntity {
        return PokemonTeamMemberEntity(this.id, this.name, this.image)
    }
}