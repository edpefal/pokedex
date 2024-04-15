package com.example.pokedex.team.domain

import com.example.pokedex.team.data.PokemonTeamRepository
import com.example.pokedex.team.presentation.PokemonTeamMemberModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeletePokemonTeamMemberUseCaseTest {

    @MockK
    private lateinit var pokemonTeamRepository: PokemonTeamRepository

    private lateinit var deletePokemonTeamMemberUseCase: DeletePokemonTeamMemberUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init()
        pokemonTeamRepository = mockk()
        deletePokemonTeamMemberUseCase = DeletePokemonTeamMemberUseCase(pokemonTeamRepository)
    }

    @Test
    fun `when deleteTeamMember gets called should call the deleteTeamMember method of the repository`() =
        runBlocking {
            val pokemonTeamMemberModel = PokemonTeamMemberModel(1, "Bulbasaur", "image_url")
            coEvery { pokemonTeamRepository.deleteTeamMember(pokemonTeamMemberModel) } returns Unit
            deletePokemonTeamMemberUseCase(pokemonTeamMemberModel)
            coVerify { pokemonTeamRepository.deleteTeamMember(pokemonTeamMemberModel) }
        }

}