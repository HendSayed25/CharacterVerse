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
                val epList = character.episode.filterNotNull()
                CharacterItem(
                    id = character.id ?: "",
                    name = character.name ?: "Unknown",
                    status = character.status ?: "Unknown",
                    species = character.species ?: "Unknown Species",
                    gender = character.gender ?: "Unknown",
                    type = character.type.orEmpty().ifBlank { "N/A" },
                    image = character.image,
                    originName = character.origin?.name ?: "Unknown Origin",
                    locationName = character.location?.name ?: "Unknown Location",
                    episodeCount = epList.size,
                    firstEpisode = epList.firstOrNull()?.name
                )
            }
    }
}

data class CharacterItem(
    val id: String,
    val name: String,
    val status: String,
    val species: String = "Unknown",
    val gender: String = "Unknown",
    val type: String = "N/A",
    val image: String? = null,
    val originName: String = "Unknown",
    val locationName: String = "Unknown",
    val episodeCount: Int = 0,
    val firstEpisode: String? = null
)