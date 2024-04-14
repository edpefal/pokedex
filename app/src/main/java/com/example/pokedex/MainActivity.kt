package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.home.presentation.screens.HomeScreen
import com.example.pokedex.home.presentation.viewmodel.HomeViewModel
import com.example.pokedex.pokemondetail.presentation.PokemonDetailScreen
import com.example.pokedex.pokemondetail.presentation.PokemonDetailViewModel
import com.example.pokedex.routes.Routes
import com.example.pokedex.team.presentation.TeamScreen
import com.example.pokedex.team.presentation.TeamViewModel
import com.example.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels()
    private val teamViewModel: TeamViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.ScreenHome.route
                    ) {
                        composable(Routes.ScreenHome.route) {
                            HomeScreen(
                                homeViewModel = homeViewModel,
                                navigationController
                            )
                        }
                        composable(
                            Routes.ScreenDetail.route,
                            arguments = listOf(navArgument("pokemon") { type = NavType.StringType })
                        ) {
                            PokemonDetailScreen(
                                navigationController,
                                it.arguments?.getString("pokemon").orEmpty(),
                                pokemonDetailViewModel
                            )
                        }
                        composable(
                            Routes.ScreenTeam.route
                        ) {
                            TeamScreen(navigationController, teamViewModel)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        Greeting("Android")
    }
}