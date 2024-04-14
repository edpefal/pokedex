package com.example.pokedex.routes

sealed class Routes(val route: String) {
    data object ScreenHome: Routes("home_screen")
    data object ScreenDetail: Routes("detail_screen/{pokemon}"){
        fun createRoute(pokemon: String) = "detail_screen/$pokemon"
    }
    data object ScreenTeam: Routes("team_screen")

}