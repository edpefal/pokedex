package com.example.pokedex.routes

sealed class Routes(val route: String) {
    data object ScreenHome: Routes("home_screen")
    data object ScreenDetail: Routes("detail_screen")
    data object ScreenTeam: Routes("team_screen")

}