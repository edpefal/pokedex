package com.example.pokedex.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.home.data.Pokemon
import com.example.pokedex.home.presentation.viewmodel.HomeViewModel
import com.example.pokedex.routes.Routes

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navigationController: NavHostController) {
    Box(Modifier.background(Color.Red)) {
        Column {
            Header(homeViewModel, Modifier.align(Alignment.End), navigationController)
            Body(navigationController)
        }
    }
}

@Composable
fun Body(navigationController: NavHostController) {
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.space_normal)),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(getPokemons()) {
                ItemPokemon(pokemon = it) {
                    navigationController.navigate(Routes.ScreenDetail.route)
                }
            }
        })
}

fun getPokemons(): List<Pokemon> {
    return listOf(Pokemon(), Pokemon(), Pokemon(), Pokemon(), Pokemon())
}

@Composable
fun ItemPokemon(pokemon: Pokemon, onItemSelected: (Pokemon) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            .width(200.dp)
            .clickable { onItemSelected(pokemon) }) {
        Column {
            AsyncImage(
                model = pokemon.image,
                contentDescription = stringResource(id = R.string.content_description_pokemon_image),
            )
            Text(
                text = pokemon.name.orEmpty(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun Header(
    homeViewModel: HomeViewModel,
    modifier: Modifier,
    navigationController: NavHostController
) {
    val textInput by homeViewModel.inputSearch.observeAsState(initial = "")
    Title(navigationController)
    SearchInput(textInput) { homeViewModel.onInputText(it) }
    SearchButton(modifier)
}

@Composable
fun SearchButton(modifier: Modifier) {
    Button(
        modifier = modifier.padding(end = dimensionResource(id = R.dimen.space_normal)),
        onClick = {}, colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContainerColor = Color(0xFF78C8F9),
            contentColor = Color.Red,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Search")
    }
}

@Composable
fun SearchInput(textInput: String, onTextChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.space_normal)),
        value = textInput,
        onValueChange = { onTextChange(it) },
        placeholder = { Text(text = stringResource(id = R.string.home_header_search_placeholder)) },
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            focusedContainerColor = Color(0xFFFAFAFA),
            unfocusedContainerColor = Color(0xFFFAFAFA),
            disabledContainerColor = Color(0xFFFAFAFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun Title(navigationController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.space_normal))
    ) {

        Text(
            text = stringResource(id = R.string.home_header_title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_header).value.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = stringResource(
                id = R.string.content_description_pokeball
            ),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.pokeball_size))
                .align(Alignment.CenterVertically)
                .clickable { navigationController.navigate(Routes.ScreenTeam.route) }
        )
    }
}
