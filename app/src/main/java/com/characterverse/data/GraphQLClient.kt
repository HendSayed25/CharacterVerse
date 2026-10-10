package com.characterverse.data

import com.apollographql.apollo.ApolloClient

object GraphQLClient {

    val client: ApolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()
}