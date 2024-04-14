package com.example.pokedex.team.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.routes.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@ExperimentalMaterial3Api
@Composable
fun TeamScreen(navigationController: NavHostController, teamViewModel: TeamViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = teamViewModel.uiState.collectAsState(initial = TeamUiState.Loading)


    Scaffold(
        topBar = {
            TeamTopAppBar {
                coroutineScope.launch {
                    navigationController.popBackStack()
                }
            }
        }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            when (val state = uiState.value) {
                TeamUiState.Empty ->
                    MessageInTeamBody(message = stringResource(id = R.string.team_empty))

                TeamUiState.Error -> MessageInTeamBody(message = stringResource(id = R.string.team_error_message))
                TeamUiState.Loading -> TeamLoadingState()
                is TeamUiState.Success -> {
                    Body(navigationController = navigationController, state.list, teamViewModel)
                }
            }
        }
    }
}

@Composable
fun MessageInTeamBody(message: String) {
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
fun TeamLoadingState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Body(
    navigationController: NavHostController,
    list: List<PokemonTeamMemberModel>,
    teamViewModel: TeamViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.space_normal)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(items = list, key = { it.id }) { pokemonTeamMember ->
                ItemPokemonTeam(
                    pokemon = pokemonTeamMember,
                    onItemSelected = {
                        navigationController.navigate(
                            Routes.ScreenDetail.createRoute(
                                pokemonTeamMember.id.toString()
                            )
                        )
                    },
                    onRemove = { teamViewModel.deleteTeamMember(pokemonTeamMember) },
                    modifier = Modifier.animateItemPlacement(tween(200))
                )
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemPokemonTeam(
    pokemon: PokemonTeamMemberModel,
    onItemSelected: (PokemonTeamMemberModel) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(confirmValueChange = { state ->
        if (state == SwipeToDismissBoxValue.EndToStart) {
            coroutineScope.launch {
                delay(1.seconds)
                onRemove()
            }
            true
        } else {
            false
        }
    })

    SwipeToDismissBox(state = swipeToDismissState, backgroundContent = {
        val backgroundColor by animateColorAsState(
            targetValue = when (swipeToDismissState.currentValue) {
                SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                SwipeToDismissBoxValue.EndToStart -> Color.Red
                SwipeToDismissBoxValue.Settled -> Color.Transparent
            }, label = "Animate bg color"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(dimensionResource(id = R.dimen.space_normal)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier.size(52.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.team_member_deleted),
                tint = Color.White
            )
        }
    }, modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onItemSelected(pokemon) }) {
            Row {
                AsyncImage(
                    modifier = Modifier.size(150.dp),
                    model = pokemon.image,
                    contentDescription = stringResource(id = R.string.content_description_pokemon_image),
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.font_size_header).value.sp,
                    text = pokemon.name,
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