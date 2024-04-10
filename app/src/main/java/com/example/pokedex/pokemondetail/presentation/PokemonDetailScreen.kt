package com.example.pokedex.pokemondetail.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(navigationController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = { MyFloatingActionButton() },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { DetailTopAppBar { coroutineScope.launch { navigationController.popBackStack() } } }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Header(innerPadding)
            PokemonType()
            PokemonStats()
        }

    }
}

@Composable
fun PokemonStats() {
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
        items(myList) {
            ItemPokemonStat(stat = it)
        }
    }
}

@Composable
fun ItemPokemonStat(stat: Float) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "HP",
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
            text = "70",
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
            progress = stat,
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),

            color = Color.Red //progress color
        )
    }


}

@Composable
fun PokemonType() {
    val myList = listOf("Water", "Grass", "Fire")
    LazyRow(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
        .fillMaxWidth()
        .padding(
            all = dimensionResource(
                id = R.dimen.space_normal
            )
        )) {
        items(myList) {
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
private fun Header(innerPadding: PaddingValues) {
    Row(
        Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png",
            contentDescription = "",
            modifier = Modifier.width(250.dp)
        )
        WeightHeightPower()

    }
}

@Composable
private fun WeightHeightPower() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = dimensionResource(id = R.dimen.space_normal))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = "004", image = R.drawable.ic_tag)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = "1.8kg", image = R.drawable.ic_weight)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = "0.6m", image = R.drawable.ic_straighten, 90f)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_normal)))
        TextWithImage(text = "power", image = R.drawable.ic_bolt)
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
fun MyFloatingActionButton() {
    FloatingActionButton(onClick = {}, contentColor = Color.White, containerColor = Color.Green) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailTopAppBar(onClickIcon: (String) -> Unit) {
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