package com.example.pokedex.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    Box(Modifier.background(Color.Red)) {
        Column {
            Header(homeViewModel, Modifier.align(Alignment.End))
            Body()
        }
    }
}

@Composable
fun Body() {

}

@Composable
fun Header(homeViewModel: HomeViewModel, modifier: Modifier) {
    val textInput by homeViewModel.inputSearch.observeAsState(initial = "")
    Title()
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
private fun Title() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.space_normal))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = stringResource(
                id = R.string.content_description_pokeball
            ), modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = stringResource(id = R.string.home_header_title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_header).value.sp,
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.space_normal))
                .align(Alignment.CenterVertically)
        )
    }
}
