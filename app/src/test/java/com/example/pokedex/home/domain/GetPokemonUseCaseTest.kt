package com.example.pokedex.home.domain

import com.example.pokedex.home.data.db.PokemonEntity
import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.home.data.model.Types
import com.example.pokedex.home.data.repository.HomeRepository
import com.example.pokedex.home.presentation.HomeUIState
import com.example.pokedex.home.presentation.model.PokemonModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPokemonUseCaseTest {

    @MockK
    private lateinit var homeRepository: HomeRepository

    private lateinit var getPokemonUseCase: GetPokemonUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init()
        homeRepository = mockk()
        getPokemonUseCase = GetPokemonUseCase(homeRepository)
    }

    @Test
    fun `when the pokemon is saved in db return the data from local storage`() = runBlocking {
        val name = "pikachu"
        val pokemonEntity =
            PokemonEntity(
                25,
                1,
                name,
                Sprites(),
                listOf(Stats()),
                listOf(Types()),
                1
            )

        coEvery { homeRepository.getPokemonFromLocalStorage(name) } returns pokemonEntity
        val response = getPokemonUseCase(name) as HomeUIState.Success
        assert(response.pokemonModel.name == pokemonEntity.name)
        coVerify { homeRepository.getPokemonFromLocalStorage(name) }
    }

    @Test
    fun `when the pokemon is not saved in db return the data from API`() = runBlocking {
        val name = "pikachu"
        val pokemonModel =
            PokemonModel(
                name = name,
                id = 25,
                image = "image"
            )

        coEvery { homeRepository.getPokemonFromLocalStorage(name) } returns null
        coEvery { homeRepository.getPokemonFromApi(name) } returns HomeUIState.Success(
            pokemonModel
        )
        getPokemonUseCase(name)
        coVerify { homeRepository.getPokemonFromApi(name) }
    }


}