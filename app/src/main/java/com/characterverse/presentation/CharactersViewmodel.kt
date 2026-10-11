package com.characterverse.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characterverse.data.CharacterItem
import com.characterverse.data.CharacterRepository
import com.characterverse.data.GraphQLClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CharactersUiState(
    val isLoading: Boolean = true,
    val rawCharacters: List<CharacterItem> = emptyList(),
    val searchQuery: String = "",
    val selectedStatusFilter: String = "All",
    val isGridView: Boolean = true,
    val selectedCharacter: CharacterItem? = null,
    val error: String? = null
) {
    val characters: List<CharacterItem>
        get() {
            return rawCharacters.filter { item ->
                val matchesSearch = searchQuery.isBlank() || item.name.contains(searchQuery, ignoreCase = true) || item.species.contains(searchQuery, ignoreCase = true)
                val matchesStatus = selectedStatusFilter == "All" || item.status.equals(selectedStatusFilter, ignoreCase = true)
                matchesSearch && matchesStatus
            }
        }
}

class CharactersViewModel : ViewModel() {

    private val repository = CharacterRepository(
        GraphQLClient.client
    )

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val characters = repository.getCharacters()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rawCharacters = characters
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to connect to the dimensional gateway"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onStatusFilterSelected(status: String) {
        _uiState.update { it.copy(selectedStatusFilter = status) }
    }

    fun onToggleViewMode() {
        _uiState.update { it.copy(isGridView = !it.isGridView) }
    }

    fun onSelectCharacter(character: CharacterItem?) {
        _uiState.update { it.copy(selectedCharacter = character) }
    }
}