package com.example.pokedex.pokemondetail.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.R
import com.example.pokedex.core.TestTags.TAG_DETAIL_HEADER
import com.example.pokedex.core.TestTags.TAG_DETAIL_LOADING
import com.example.pokedex.core.TestTags.TAG_DETAIL_MESSAGE_BODY
import com.example.pokedex.home.data.model.Sprites
import com.example.pokedex.home.data.model.Stats
import com.example.pokedex.pokemondetail.data.PokemonDetailModel
import org.junit.Rule
import org.junit.Test

class PokemonDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources


    private val pokemonDetailModel =
        PokemonDetailModel("25", "1", "pikachu", Sprites(), listOf(Stats()), listOf("Type"), "1")


    @Test
    fun whenPokemonDetailStateLoading_Then_ShowLoadingIndicator() {
        composeTestRule.setContent {
            PokemonDetailContent(
                uiState = PokemonDetailUIState.Loading,
                innerPadding = PaddingValues(16.dp),
                addPokemonUIState = AddPokemonUIState.Empty,
                updateState = {}
            )
        }
        composeTestRule.onNodeWithTag(TAG_DETAIL_LOADING).assertIsDisplayed()
    }

    @Test
    fun whenPokemonDetailStateError_Then_ShowErrorMessageInBody() {
        composeTestRule.setContent {
            PokemonDetailContent(
                uiState = PokemonDetailUIState.Error,
                innerPadding = PaddingValues(16.dp),
                addPokemonUIState = AddPokemonUIState.Empty,
                updateState = {}
            )
        }
        composeTestRule.onNodeWithTag(TAG_DETAIL_MESSAGE_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DETAIL_MESSAGE_BODY)
            .assertTextEquals(resources.getString(R.string.pokemon_detail_error_message))
    }

    @Test
    fun whenPokemonDetailStateSuccess_Then_ShowPokemonInfo() {
        composeTestRule.setContent {
            PokemonDetailContent(
                uiState = PokemonDetailUIState.Success(pokemonDetailModel),
                innerPadding = PaddingValues(16.dp),
                addPokemonUIState = AddPokemonUIState.Empty,
                updateState = {}
            )
        }
        composeTestRule.onNodeWithTag(TAG_DETAIL_HEADER).assertIsDisplayed()
    }
}