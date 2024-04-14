package com.example.pokedex.team.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.team.domain.DeletePokemonTeamMemberUseCase
import com.example.pokedex.team.domain.GetPokemonTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    val getPokemonTeamUseCase: GetPokemonTeamUseCase,
    val deletePokemonTeamMemberUseCase: DeletePokemonTeamMemberUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<TeamUiState>(TeamUiState.Loading)
    val uiState: StateFlow<TeamUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getPokemonTeamUseCase()
                .catch {
                    _uiState.value = TeamUiState.Error
                }
                .collect { members ->
                    if(members.isEmpty()) {
                        _uiState.value = TeamUiState.Empty
                    } else {
                        _uiState.value = TeamUiState.Success(members)
                    }

                }
        }
    }

    fun deleteTeamMember(pokemonTeamMemberModel: PokemonTeamMemberModel) {
        viewModelScope.launch {
            deletePokemonTeamMemberUseCase(pokemonTeamMemberModel)
        }
    }
}
