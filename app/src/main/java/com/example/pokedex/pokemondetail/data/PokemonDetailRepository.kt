package com.example.pokedex.pokemondetail.data

import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.model.Types
import com.example.pokedex.pokemondetail.presentation.PokemonDetailUIState
import com.example.pokedex.team.data.db.PokemonTeamMemberEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(private val pokemonDetailDao: PokemonDetailDao) {

    fun getPokemonToShow(pokemonNameOrNumber: String): Flow<PokemonDetailUIState> {
        return pokemonDetailDao.getPokemonById(pokemonNameOrNumber).map {
            PokemonDetailUIState.Success(it.toPokemonDetailModel())
        }
    }

    suspend fun addPokemonTeamMember(pokemonDetailModel: PokemonDetailModel) {
        pokemonDetailDao.addPokemonTeamMember(pokemonDetailModel.toPokemonTemMemberEntity())
    }

    private fun PokemonEntity.toPokemonDetailModel(): PokemonDetailModel {
        return PokemonDetailModel(
            this.id.toString(),
            "${(this.height / 10f)}m".uppercase(),
            this.name.uppercase(),
            this.sprites,
            this.stats,
            this.types.toListOfStrings(),
            "${(this.weight / 10f)}kg"
        )
    }

    private fun List<Types>.toListOfStrings(): List<String> {
        return this.flatMap {
            it.type?.name?.uppercase()?.let(::listOf) ?: emptyList()
        }
    }

    private fun PokemonDetailModel.toPokemonTemMemberEntity(): PokemonTeamMemberEntity {
        return PokemonTeamMemberEntity(
            this.id.toInt(),
            this.name,
            this.sprites.frontDefault.orEmpty()
        )
    }
}