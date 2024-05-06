package com.example.pokedex.pokemondetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import com.example.pokedex.pokemondetail.domain.AddPokemonResult
import com.example.pokedex.pokemondetail.domain.AddPokemonTeamMemberUseCase
import com.example.pokedex.pokemondetail.domain.ShowPokemonInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val showPokemonInfoUseCase: ShowPokemonInfoUseCase,
    private val addPokemonTeamMemberUseCase: AddPokemonTeamMemberUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUIState>(PokemonDetailUIState.Loading)
    val uiState: StateFlow<PokemonDetailUIState> = _uiState.asStateFlow()

    private val _addPokemonUiState = MutableStateFlow<AddPokemonUIState>(AddPokemonUIState.Empty)
    val addPokemonUiState: StateFlow<AddPokemonUIState> = _addPokemonUiState.asStateFlow()

    private var pokemonDetailModel: PokemonDetailModel? = null


    fun getPokemonDetailInfo(pokemon: String) {
        viewModelScope.launch {
            showPokemonInfoUseCase(pokemon)
                .catch { _uiState.value = PokemonDetailUIState.Error }
                .collect {
                    pokemonDetailModel = it
                    _uiState.value = PokemonDetailUIState.Success(it)
                }


        }
    }

    fun addPokemonTeamMember() {
        viewModelScope.launch {
            pokemonDetailModel?.let {
                val response = addPokemonTeamMemberUseCase(it)
                when (response) {
                    AddPokemonResult.AlreadyAdded -> _addPokemonUiState.value = AddPokemonUIState.AlreadyExist
                    AddPokemonResult.Failure -> _addPokemonUiState.value = AddPokemonUIState.MaxNumber
                    AddPokemonResult.Success -> _addPokemonUiState.value = AddPokemonUIState.Success
                }
            }
        }
    }

    fun updateState(state: AddPokemonUIState) {
        _addPokemonUiState.value = state
    }

}