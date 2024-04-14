package com.example.pokedex.home.data.repository

import com.example.pokedex.home.data.db.PokemonDao
import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.model.PokemonResponse
import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.network.ApiResult
import com.example.pokedex.home.data.network.HomeService
import com.example.pokedex.home.presentation.HomeUIState
import com.example.pokedex.home.presentation.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val pokemonDao: PokemonDao
) {

    suspend fun getPokemon(pokemon: String): HomeUIState {
        return getPokemonFromLocalStorage(pokemon)?.let {
            HomeUIState.Success(it.toPokemonModel())
        } ?: run {
            getPokemonFromApi(pokemon)
        }
    }

    suspend fun getPokemonFromApi(pokemonNameOrNumber: String): HomeUIState {
        return when (val result = homeService.getPokemon(pokemonNameOrNumber)) {
            is ApiResult.Error -> HomeUIState.Error
            is ApiResult.Success -> {
                savePokemonToLocalStorage(result.data)
                HomeUIState.Success(
                    PokemonModel(
                        result.data.name.orEmpty(),
                        result.data.sprites?.frontDefault ?: "",
                        result.data.id ?: 0
                    )
                )
            }
        }
    }

    private suspend fun savePokemonToLocalStorage(pokemonResponse: PokemonResponse) {
        withContext(Dispatchers.IO) {
            pokemonDao.addPokemon(pokemonResponse.toPokemonEntity())
        }

    }

    suspend fun addPokemonToLocalStorage(pokemonResponse: PokemonResponse) {
        //pokemonDao.addPokemon(pokemonResponse.toPokemonEntity())
    }

    suspend fun getPokemonFromLocalStorage(pokemon: String): PokemonEntity? {
        return pokemonDao.getPokemonByIdOrName(pokemon)
    }

    private fun PokemonResponse.toPokemonEntity(): PokemonEntity {
        return PokemonEntity(
            this.id ?: 0,
            this.height ?: 0,
            this.name.orEmpty(),
            this.sprites ?: Sprites(),
            this.stats ?: arrayListOf(),
            this.types ?: arrayListOf(),
            this.weight ?: 0
        )
    }

    private fun PokemonEntity.toPokemonModel(): PokemonModel {
        return PokemonModel(
            this.name,
            this.sprites.frontDefault.orEmpty(),
            this.id
        )
    }
}