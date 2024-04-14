package com.example.pokedex.pokemondetail.presentation

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    navigationController: NavHostController,
    pokemon: String,
    pokemonDetailViewModel: PokemonDetailViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = pokemonDetailViewModel.uiState.collectAsState()
    val addPokemonUIState = pokemonDetailViewModel.addPokemonUiState.collectAsState()
    pokemonDetailViewModel.getPokemonDetailInfo(pokemon)


    Scaffold(
        floatingActionButton = { AddPokemonFab(pokemonDetailViewModel) },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { DetailTopAppBar { coroutineScope.launch { navigationController.popBackStack() } } }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            when (val state = uiState.value) {
                PokemonDetailUIState.Error ->
                    ErrorState(message = stringResource(id = R.string.pokemon_detail_error_message))

                PokemonDetailUIState.Loading ->
                    PokemonDetailLoadingState()

                is PokemonDetailUIState.Success -> {
                    Content(
                        innerPadding,
                        state.pokemonDetailModel,
                        addPokemonUIState,
                        pokemonDetailViewModel
                    )
                }
            }

        }

    }
}

@Composable
private fun Content(
    innerPadding: PaddingValues,
    pokemonDetailModel: PokemonDetailModel,
    addPokemonUIState: State<AddPokemonUIState>,
    pokemonDetailViewModel: PokemonDetailViewModel
) {
    Header(innerPadding, pokemonDetailModel)
    PokemonType(pokemonDetailModel)
    PokemonStats(pokemonDetailModel)

    val context = LocalContext.current
    when (addPokemonUIState.value) {
        AddPokemonUIState.Empty -> {}
        AddPokemonUIState.AlreadyExist -> Toast.makeText(
            context,
            stringResource(id = R.string.pokemon_already_exists),
            Toast.LENGTH_SHORT
        ).show()

        AddPokemonUIState.Success -> {
            Toast.makeText(
                context,
                stringResource(id = R.string.pokemon_added),
                Toast.LENGTH_SHORT
            ).show()
        }

        AddPokemonUIState.MaxNumber -> Toast.makeText(
            context,
            stringResource(id = R.string.pokemon_max_number),
            Toast.LENGTH_SHORT
        ).show()
    }
    pokemonDetailViewModel.updateState(AddPokemonUIState.Empty)
}

@Composable
fun PokemonDetailLoadingState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = dimensionResource(id = R.dimen.space_normal)),
            text = message, fontSize = dimensionResource(
                id = R.dimen.font_size_empty_state_message
            ).value.sp
        )

    }
}

@Composable
fun PokemonStats(pokemonDetailModel: PokemonDetailModel) {
    val myList = listOf(0.45f, 0.59f, 0.7f, 0.2f, 0.8f)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(50.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = dimensionResource(
                    id = R.dimen.space_normal
                )
            )
    ) {
        items(pokemonDetailModel.stats) {
            ItemPokemonStat(stats = it)
        }
    }
}

@Composable
fun ItemPokemonStat(stats: Stats) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = stats.stat?.name.orEmpty().uppercase(),
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.space_medium))
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = stats.baseStat.toString(),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.space_medium),
                    end = dimensionResource(
                        id = R.dimen.space_normal
                    )
                )

        )
        LinearProgressIndicator(
            progress = ((stats.baseStat?.toFloat() ?: 0F) / 100F),
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),

            color = Color.Red
        )
    }


}

@Composable
fun PokemonType(pokemonDetailModel: PokemonDetailModel) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = dimensionResource(
                    id = R.dimen.space_normal
                )
            )
    ) {
        items(pokemonDetailModel.types) {
            ItemPokemonType(type = it)
        }
    }
}

@Composable
fun ItemPokemonType(type: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.Red)) {
        Text(
            text = "$type",
            Modifier.padding(8.dp),
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
private fun Header(innerPadding: PaddingValues, pokemonDetailModel: PokemonDetailModel) {
    Row(
        Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = pokemonDetailModel.sprites.frontDefault,
            contentDescription = stringResource(id = R.string.content_description_pokemon_detail_image),
            modifier = Modifier.width(dimensionResource(id = R.dimen.pokemon_detail_image_width))
        )
        WeightHeightPower(pokemonDetailModel)

    }
}

@Composable
private fun WeightHeightPower(pokemonDetailModel: PokemonDetailModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = dimensionResource(id = R.dimen.space_normal))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        Text(text = pokemonDetailModel.name, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = pokemonDetailModel.id, image = R.drawable.ic_tag)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = pokemonDetailModel.weight, image = R.drawable.ic_weight)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = pokemonDetailModel.height, image = R.drawable.ic_straighten, 90f)

    }
}

@Composable
fun TextWithImage(text: String, @DrawableRes image: Int, rotate: Float = 0f) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = "",
            Modifier.rotate(rotate)
        )
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
    }

}

@Composable
fun AddPokemonFab(pokemonDetailViewModel: PokemonDetailViewModel) {
    FloatingActionButton(onClick = {
        pokemonDetailViewModel.addPokemonTeamMember()
    }, contentColor = Color.White, containerColor = Color.Green) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailTopAppBar(onClickIcon: (String) -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.pokemon_detail_toolbar_title),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = {
            IconButton(onClick = { onClickIcon("Atras") }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
    )
}