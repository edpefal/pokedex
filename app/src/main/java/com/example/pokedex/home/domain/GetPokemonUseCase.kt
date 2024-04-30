package com.example.pokedex.home.domain

import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.model.PokemonResponse
import com.example.pokedex.home.data.repository.HomeRepository
import com.example.pokedex.home.presentation.HomeUIState
import com.example.pokedex.home.presentation.model.PokemonModel
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke (pokemonNameOrNumber: String): PokemonModel? {
        return homeRepository.getPokemonFromLocalStorage(pokemonNameOrNumber)?.toPokemonModel() ?: run {
            homeRepository.getPokemonFromApi(pokemonNameOrNumber)?.toPokemonModel()
        }
    }

    fun PokemonEntity.toPokemonModel(): PokemonModel {
        return PokemonModel(
            this.name,
            this.sprites.frontDefault.orEmpty(),
            this.id
        )
    }

    fun PokemonResponse.toPokemonModel(): PokemonModel {
        return PokemonModel(
            this.name.orEmpty(),
            this.sprites?.frontDefault.orEmpty(),
            this.id ?: 0
        )
    }
}