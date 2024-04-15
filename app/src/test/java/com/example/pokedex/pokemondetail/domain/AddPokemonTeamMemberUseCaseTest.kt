package com.example.pokedex.pokemondetail.domain

import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.pokemondetail.presentation.AddPokemonUIState
import com.example.pokedex.team.data.PokemonTeamRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddPokemonTeamMemberUseCaseTest {

    @MockK
    private lateinit var pokemonTeamRepository: PokemonTeamRepository

    private lateinit var addPokemonTeamMemberUseCase: AddPokemonTeamMemberUseCase

    @Before
    fun onBefore() {
        pokemonTeamRepository = mockk()
        addPokemonTeamMemberUseCase = AddPokemonTeamMemberUseCase(pokemonTeamRepository)
    }

    @Test
    fun `when pokemon already exist in db should return AddPokemonUIState AlreadyExist`() =
        runBlocking {
            val id = "25"
            val pokemonDetailModel = PokemonDetailModel(
                id, "1", "pikachu", Sprites(), listOf(Stats()),
                listOf(""), "1"
            )
            coEvery { pokemonTeamRepository.pokemonAlreadyInTeam(id) } returns true
            val response = addPokemonTeamMemberUseCase(pokemonDetailModel)
            assert(response == AddPokemonUIState.AlreadyExist)


        }

    @Test
    fun `when pokemon is not in db and the team is less than six pokemon should return AddPokemonUIState Success`() =
        runBlocking {
            val id = "25"
            val pokemonDetailModel = PokemonDetailModel(
                id, "1", "pikachu", Sprites(), listOf(Stats()),
                listOf(""), "1"
            )
            coEvery { pokemonTeamRepository.pokemonAlreadyInTeam(id) } returns false
            coEvery { pokemonTeamRepository.getTeamCount() } returns 5
            coEvery { pokemonTeamRepository.addPokemonTeamMember(pokemonDetailModel) } returns Unit
            val response = addPokemonTeamMemberUseCase(pokemonDetailModel)
            assert(response == AddPokemonUIState.Success)
        }

    @Test
    fun `when pokemon is not in db and the team is equal than six pokemon should return AddPokemonUIState MaxNumber`() =
        runBlocking {
            val id = "25"
            val pokemonDetailModel = PokemonDetailModel(
                id, "1", "pikachu", Sprites(), listOf(Stats()),
                listOf(""), "1"
            )
            coEvery { pokemonTeamRepository.pokemonAlreadyInTeam(id) } returns false
            coEvery { pokemonTeamRepository.getTeamCount() } returns 6
            coEvery { pokemonTeamRepository.addPokemonTeamMember(pokemonDetailModel) } returns Unit
            val response = addPokemonTeamMemberUseCase(pokemonDetailModel)
            assert(response == AddPokemonUIState.MaxNumber)


        }

}