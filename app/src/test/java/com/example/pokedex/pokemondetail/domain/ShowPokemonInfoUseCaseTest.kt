package com.example.pokedex.pokemondetail.domain

import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.pokemondetail.data.PokemonDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ShowPokemonInfoUseCaseTest {

    @MockK
    private lateinit var pokemonDetailRepository: PokemonDetailRepository

    private lateinit var showPokemonInfoUseCase: ShowPokemonInfoUseCase;

    @Before
    fun onBefore() {
        pokemonDetailRepository = mockk()
        showPokemonInfoUseCase = ShowPokemonInfoUseCase(pokemonDetailRepository)
    }

    @Test
    fun `invoke should return a flow of PokemonDetailModel`() = runBlocking {
        val pokemonName = "pikachu"
        val pokemonDetailModel = PokemonDetailModel(
            "25", "1", pokemonName, Sprites(), listOf(Stats()),
            listOf(""), "1"
        )
        coEvery { pokemonDetailRepository.getPokemonToShow(pokemonName) } returns flowOf(pokemonDetailModel)
        val response = showPokemonInfoUseCase(pokemonName)
        coVerify { pokemonDetailRepository.getPokemonToShow(pokemonName) }
        response.collect{
            assert(it == pokemonDetailModel)
        }

    }


}