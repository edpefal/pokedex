package com.example.pokedex.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.home.domain.GetPokemonUseCase
import com.example.pokedex.home.presentation.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    ViewModel() {
    private val _inputSearch = MutableLiveData<String>()
    val inputSearch: LiveData<String> = _inputSearch

    private val _isSearchEnable = MutableLiveData<Boolean>()
    val isSearchEnable: LiveData<Boolean> = _isSearchEnable

    private val _homeViewState = MutableLiveData<HomeUIState>()
    val homeViewState: LiveData<HomeUIState> = _homeViewState

    fun onInputText(inputText: String) {
        _inputSearch.value = inputText
        _isSearchEnable.value = isSearchEnable()
    }


    fun isSearchEnable() = _inputSearch.value?.isNotEmpty() == true

    fun onSearchSelected() {
        if (isSearchEnable()) {
            viewModelScope.launch {
                val response = getPokemonUseCase.invoke(_inputSearch.value.orEmpty())
                response?.let {
                    _homeViewState.postValue(HomeUIState.Success(it))
                } ?: run {
                    _homeViewState.postValue(HomeUIState.Error)
                }
            }
        }
    }
}