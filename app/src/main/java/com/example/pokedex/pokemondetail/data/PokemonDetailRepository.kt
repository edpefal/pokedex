package com.example.pokedex.pokemondetail.data

import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.model.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(private val pokemonDetailDao: PokemonDetailDao) {

    fun getPokemonToShow(pokemonNameOrNumber: String): Flow<PokemonDetailModel> {
        return pokemonDetailDao.getPokemonById(pokemonNameOrNumber).map {
            it.toPokemonDetailModel()
        }
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
}