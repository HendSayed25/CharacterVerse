
package com.characterverse.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.characterverse.data.CharacterItem
import com.characterverse.data.CharacterRepository
import com.characterverse.data.GraphQLClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CharactersUiState(
    val isLoading: Boolean = true,
    val characters: List<CharacterItem> = emptyList(),
    val error: String? = null
)

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
            _uiState.value = CharactersUiState(isLoading = true)

            try {
                val characters = repository.getCharacters()
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    characters = characters
                )
            } catch (e: Exception) {
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }
}