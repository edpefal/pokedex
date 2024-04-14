package com.example.pokedex.team.presentation

sealed class TeamUiState {
    class Success(val list: List<PokemonTeamMemberModel>): TeamUiState()
    object Loading: TeamUiState()
    object Error: TeamUiState()
    object Empty: TeamUiState()
}