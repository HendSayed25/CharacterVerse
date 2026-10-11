package com.characterverse.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.characterverse.R
import com.characterverse.data.CharacterItem
import com.characterverse.presentation.composable.*
import com.characterverse.ui.theme.CharacterVerseTheme
import com.characterverse.ui.theme.DarkBackground

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val error = uiState.error
    val selectedCharacter = uiState.selectedCharacter

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CharacterVerseTopBar(
                isGridView = uiState.isGridView,
                onToggleViewMode = viewModel::onToggleViewMode
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SciFiSearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            StatusFilterChips(
                selectedStatus = uiState.selectedStatusFilter,
                onStatusSelected = viewModel::onStatusFilterSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            val allFilter = stringResource(R.string.filter_all)

            when {
                uiState.isLoading -> {
                    LoadingSkeleton(isGridView = uiState.isGridView)
                }

                error != null -> {
                    ErrorView(
                        errorMessage = error,
                        onRetry = viewModel::loadCharacters
                    )
                }

                uiState.characters.isEmpty() -> {
                    EmptySearchState(
                        searchQuery = uiState.searchQuery,
                        onResetFilters = {
                            viewModel.onSearchQueryChange("")
                            viewModel.onStatusFilterSelected(allFilter)
                        }
                    )
                }

                else -> {
                    AnimatedContent(
                        targetState = uiState.isGridView,
                        label = "ViewModeTransition"
                    ) { isGrid ->
                        if (isGrid) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = uiState.characters,
                                    key = { it.id }
                                ) { character ->
                                    CharacterGridCard(
                                        character = character,
                                        onClick = { viewModel.onSelectCharacter(character) }
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = uiState.characters,
                                    key = { it.id }
                                ) { character ->
                                    CharacterListCard(
                                        character = character,
                                        onClick = { viewModel.onSelectCharacter(character) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (selectedCharacter != null) {
            CharacterDetailSheet(
                character = selectedCharacter,
                onDismiss = { viewModel.onSelectCharacter(null) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharactersScreenPreview() {
    val sampleCharacters = listOf(
        CharacterItem(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            type = "Genius",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            originName = "Earth (C-137)",
            locationName = "Citadel of Ricks",
            episodeCount = 51,
            firstEpisode = "Pilot"
        ),
        CharacterItem(
            id = "2",
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            type = "",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            originName = "Earth (C-137)",
            locationName = "Earth (Replacement Dimension)",
            episodeCount = 51,
            firstEpisode = "Pilot"
        )
    )

    CharacterVerseTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = DarkBackground) {
            Column(modifier = Modifier.padding(16.dp)) {
                CharacterVerseTopBar(isGridView = true, onToggleViewMode = {})
                SciFiSearchBar(query = "", onQueryChange = {})
                Spacer(modifier = Modifier.height(12.dp))
                StatusFilterChips(selectedStatus = "All", onStatusSelected = {})
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sampleCharacters) { character ->
                        CharacterGridCard(character = character, onClick = {})
                    }
                }
            }
        }
    }
}