package com.example.pokedex.home.domain

import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.repository.HomeRepository
import com.example.pokedex.home.presentation.HomeUIState
import com.example.pokedex.home.presentation.model.PokemonModel
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke (pokemonNameOrNumber: String): HomeUIState {
        return homeRepository.getPokemonFromLocalStorage(pokemonNameOrNumber)?.let {
            HomeUIState.Success(it.toPokemonModel())
        } ?: run {
            homeRepository.getPokemonFromApi(pokemonNameOrNumber)
        }
    }

    fun PokemonEntity.toPokemonModel(): PokemonModel {
        return PokemonModel(
            this.name,
            this.sprites.frontDefault.orEmpty(),
            this.id
        )
    }
}