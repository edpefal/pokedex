package com.example.pokedex.team.domain

import com.example.pokedex.team.data.PokemonTeamRepository
import com.example.pokedex.team.presentation.PokemonTeamMemberModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


@MockK
private lateinit var pokemonTeamRepository: PokemonTeamRepository

private lateinit var getPokemonTeamUseCase: GetPokemonTeamUseCase

class GetPokemonTeamUseCaseTest {

    @Before
    fun onBefore() {
        MockKAnnotations.init()
        pokemonTeamRepository = mockk()
        getPokemonTeamUseCase = GetPokemonTeamUseCase(pokemonTeamRepository)
    }

    @Test
    fun `when getTeam gets called should return a flow of PokemontTeamMemberModel`() = runBlocking {
        val pokemonTeamList = listOf(
            PokemonTeamMemberModel(1, "Bulbasaur", "image_url"),
            PokemonTeamMemberModel(2, "Ivysaur", "image_url"),
        )
        coEvery { pokemonTeamRepository.getTeam() } returns flowOf(
            pokemonTeamList
        )
        val result = getPokemonTeamUseCase()
        result.collect {
            assert( it == pokemonTeamList)
        }
    }

}