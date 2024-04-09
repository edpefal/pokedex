package com.example.pokedex.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _inputSearch = MutableLiveData<String>()
    val inputSearch: LiveData<String> = _inputSearch

    fun onInputText(inputText: String){
        _inputSearch.value = inputText
    }
}