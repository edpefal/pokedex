package com.example.pokedex.home.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.R
import com.example.pokedex.core.TestTags.TAG_HOME_CARD_POKEMON
import com.example.pokedex.core.TestTags.TAG_HOME_LOADING_INDICATOR
import com.example.pokedex.core.TestTags.TAG_HOME_MESSAGE_BODY
import com.example.pokedex.core.TestTags.TAG_HOME_SEARCH_RESULT
import com.example.pokedex.core.TestTags.TAG_SEARCH_BUTTON
import com.example.pokedex.home.presentation.model.PokemonModel
import com.example.pokedex.home.presentation.screens.Header
import com.example.pokedex.home.presentation.screens.HomeContent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private var resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources

    @Before
    fun onBefore() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun whenSearchFieldIsEmpty_Then_SearchButtonIsNotEnable() {
        composeTestRule.setContent {
            Header(
                modifier = Modifier.fillMaxSize(),
                textInput = "",
                searchEnable = false,
                onInputText = {},
                onSearchSelected = {}) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_SEARCH_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun whenSearchFieldIsNotEmpty_Then_SearchButtonIsEnable() {
        composeTestRule.setContent {
            Header(
                modifier = Modifier.fillMaxSize(),
                textInput = "",
                searchEnable = true,
                onInputText = {},
                onSearchSelected = {}) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_SEARCH_BUTTON).assertIsEnabled()
    }

    @Test
    fun whenHomeStateEmpty_Then_ShowEmptyMessageInBody() {
        composeTestRule.setContent {
            HomeContent(
                textInput = "pikachu",
                searchEnable = true,
                navigationController = navController,
                homeUiState = HomeUIState.Empty,
                onInputText = {}
            ) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_HOME_MESSAGE_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_HOME_MESSAGE_BODY)
            .assertTextEquals(resources.getString(R.string.home_empty_pokemon))
    }

    @Test
    fun whenHomeStateError_Then_ShowErrorMessageInBody() {
        composeTestRule.setContent {
            HomeContent(
                textInput = "pikachu",
                searchEnable = true,
                navigationController = navController,
                homeUiState = HomeUIState.Error,
                onInputText = {}
            ) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_HOME_MESSAGE_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_HOME_MESSAGE_BODY)
            .assertTextEquals(resources.getString(R.string.home_error_message))
    }

    @Test
    fun whenHomeStateLoading_Then_ShowLoadingIndicator() {
        composeTestRule.setContent {
            HomeContent(
                textInput = "pikachu",
                searchEnable = true,
                navigationController = navController,
                homeUiState = HomeUIState.Loading,
                onInputText = {}
            ) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_HOME_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun whenHomeStateSuccess_Then_ShowSearchResult() {
        composeTestRule.setContent {
            HomeContent(
                textInput = "pikachu",
                searchEnable = true,
                navigationController = navController,
                homeUiState = HomeUIState.Success(PokemonModel("", "", 1)),
                onInputText = {}
            ) {

            }
        }
        composeTestRule.onNodeWithTag(TAG_HOME_SEARCH_RESULT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_HOME_CARD_POKEMON).assertIsDisplayed()

    }
}