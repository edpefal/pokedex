package com.example.pokedex.team.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.home.data.Pokemon
import com.example.pokedex.routes.Routes
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun TeamScreen(navigationController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TeamTopAppBar { coroutineScope.launch {
            navigationController.popBackStack() } } }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Body(navigationController = navigationController)
        }

    }
}

@Composable
fun Body(navigationController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.space_normal)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(getPokemons()) {
                ItemPokemonTeam(pokemon = it) {
                    navigationController.navigate(Routes.ScreenDetail.route)
                }
            }
        })
}

fun getPokemons(): List<Pokemon> {
    return listOf(Pokemon(), Pokemon(), Pokemon(), Pokemon(), Pokemon())
}

@Composable
fun ItemPokemonTeam(pokemon: Pokemon, onItemSelected: (Pokemon) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onItemSelected(pokemon) }) {
        Row {
            AsyncImage(
                model = pokemon.image,
                contentDescription = stringResource(id = R.string.content_description_pokemon_image),
            )
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_size_header).value.sp,
                text = pokemon.name.orEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        horizontal = dimensionResource(
                            id = R.dimen.space_normal
                        )
                    )
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TeamTopAppBar(onClickIcon: (String) -> Unit) {
    TopAppBar(
        title = { Text(text = "Mi primera toolbar") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red),
        navigationIcon = {
            IconButton(onClick = { onClickIcon("Atras") }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
    )
}