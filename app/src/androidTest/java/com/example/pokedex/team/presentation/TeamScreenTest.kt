package com.example.pokedex.team.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.R
import com.example.pokedex.core.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeamScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private var resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources

    private val pokemonTeamMemberModelList = listOf(PokemonTeamMemberModel(25, "pikachu", "image"))

    @Before
    fun onBefore() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun whenTeamScreenStateLoading_Then_ShowLoadingIndicator() {
        composeTestRule.setContent {
            TeamContent(
                innerPadding = PaddingValues(16.dp),
                uiState = TeamUiState.Loading,
                navigationController = navController,
                deleteTeamMember = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_LOADING).assertIsDisplayed()
    }

    @Test
    fun whenTeamScreenStateEmpty_Then_ShowEmptyMessage() {
        composeTestRule.setContent {
            TeamContent(
                innerPadding = PaddingValues(16.dp),
                uiState = TeamUiState.Empty,
                navigationController = navController,
                deleteTeamMember = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_MESSAGE_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_MESSAGE_BODY)
            .assertTextEquals(resources.getString(R.string.team_empty))
    }

    @Test
    fun whenTeamScreenStateError_Then_ShowErrorMessage() {
        composeTestRule.setContent {
            TeamContent(
                innerPadding = PaddingValues(16.dp),
                uiState = TeamUiState.Error,
                navigationController = navController,
                deleteTeamMember = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_MESSAGE_BODY).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_MESSAGE_BODY)
            .assertTextEquals(resources.getString(R.string.team_error_message))
    }

    @Test
    fun whenTeamScreenStateSuccess_Then_ShowTeam() {
        composeTestRule.setContent {
            TeamContent(
                innerPadding = PaddingValues(16.dp),
                uiState = TeamUiState.Success(pokemonTeamMemberModelList),
                navigationController = navController,
                deleteTeamMember = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TAG_TEAM_CARD_MEMBER).assertIsDisplayed()
    }

}