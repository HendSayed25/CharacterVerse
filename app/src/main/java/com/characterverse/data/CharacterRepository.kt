
package com.characterverse.data

import com.apollographql.apollo.ApolloClient
import com.characterexplorer.CharactersQuery

class CharacterRepository(
    private val apolloClient: ApolloClient
) {
    suspend fun getCharacters(): List<CharacterItem> {
        val response = apolloClient
            .query(CharactersQuery())
            .execute()

        if (response.hasErrors()) {
            throw Exception(
                response.errors?.joinToString { it.message }
                    ?: "Failed to load characters"
            )
        }

        return response.data
            ?.characters
            ?.results
            .orEmpty()
            .filterNotNull()
            .map { character ->
                CharacterItem(
                    id = character.id,
                    name = character.name ?: "Unknown",
                    status = character.status ?: "Unknown",
                    image = character.image
                )
            }
    }
}

data class CharacterItem(
    val id: String?,
    val name: String,
    val status: String,
    val image: String?
)