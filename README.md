# CharacterVerse 🛸

CharacterVerse is an Android application built with Kotlin and Jetpack Compose to explore characters from the Rick and Morty universe using GraphQL and Apollo Kotlin.

This project is a learning project focused on understanding GraphQL, integrating Apollo Kotlin into an Android application, and fetching API data using a structured application architecture.

## 🚀 Technologies Used

* **Kotlin** — Android application development.
* **Jetpack Compose** — Declarative UI development.
* **GraphQL** — Querying API data by selecting specific fields.
* **Apollo Kotlin 5.2.0** — GraphQL client and Kotlin code generation.
* **Coroutines** — Asynchronous network operations.
* **StateFlow** — Managing and observing UI state.
* **ViewModel** — Managing screen-related state.
* **Rick and Morty API** — Character data source.

## 🌐 API

**GraphQL Endpoint:**

https://rickandmortyapi.com/graphql

The Rick and Morty API provides information about characters, episodes, and locations from the series.

## 📁 Project Structure

```text
app/src/main/
├── java/com/characterverse/
│   ├── MainActivity.kt
│   ├── CharactersScreen.kt
│   ├── CharactersViewModel.kt
│   └── data/
│       ├── ApolloClientProvider.kt
│       └── CharacterRepository.kt
├── graphql/
│   ├── schema.graphqls
│   └── Characters.graphql
└── AndroidManifest.xml
```

**Note:** Apollo-generated Kotlin files are stored under the build directory and are not manually maintained source files.

## 📚 Understanding GraphQL

GraphQL is an API query language that allows a client to request exactly the fields it needs.

Unlike a typical REST API, where each endpoint generally provides a predefined response structure, GraphQL allows clients to specify the fields they want in a query.

For example:

```graphql
query Characters {
  characters(page: 1) {
    results {
      id
      name
      status
      image
    }
  }
}
```

This query requests the first page of characters and selects their IDs, names, statuses, and image URLs.

### Key concepts

* **Query:** Reads data from the server.
* **Fields:** Specify which properties should be returned.
* **Arguments:** Customize a request, such as selecting a page.
* **Schema:** Defines the API's available types, fields, queries, and relationships.
* **Response:** Contains the requested data and may include GraphQL errors.

## ⚙️ Apollo Kotlin Setup

Apollo Kotlin is a GraphQL client that integrates GraphQL operations into Kotlin applications.

It provides:

* Execution of GraphQL queries.
* Parsing of GraphQL responses into generated Kotlin models.
* Type-safe access to query results.
* Compile-time validation of operations against the schema.
* Generated code for query operations and their response models.

### 1. Add the Apollo plugin

The Apollo Gradle plugin is configured in the app module's `build.gradle.kts` file.

```kotlin
plugins {
    id("com.apollographql.apollo") version "5.2.0"
}
```

The Apollo runtime dependency is also required:

```kotlin
implementation("com.apollographql.apollo:apollo-runtime:5.2.0")
```

If the project uses a Gradle version catalog, the dependency can instead be declared through the corresponding library alias.

### 2. Configure Apollo

Apollo is configured with a service name, a generated-code package, and a schema location.

```kotlin
apollo {
    service("service") {
        packageName.set("com.characterverse")

        introspection {
            endpointUrl.set(
                "https://rickandmortyapi.com/graphql"
            )
            schemaFile.set(
                file("src/main/graphql/schema.graphqls")
            )
        }
    }
}
```

**Purpose of each setting:**

* `service("service")`: Defines the Apollo service configuration.
* `packageName`: Specifies the package for generated Kotlin classes.
* `endpointUrl`: Specifies the GraphQL endpoint used for schema introspection.
* `schemaFile`: Specifies where the downloaded schema is stored.

### 3. Download the schema

Run the following command from the project root in the Android Studio Terminal:

```powershell
.\gradlew.bat :app:downloadServiceApolloSchemaFromIntrospection
```

This downloads the GraphQL schema into `schema.graphqls`.

The schema describes what the API supports. Apollo uses it to validate queries and generate Kotlin code.

### 4. Create a GraphQL query

GraphQL operations are stored in `.graphql` files under:

```text
app/src/main/graphql/
```

For example:

```text
Characters.graphql
```

A `.graphql` file is a plain-text source file containing GraphQL operations. It is not a Kotlin file or a JSON file.

### 5. Generate Kotlin source code

Run:

```powershell
.\gradlew.bat :app:generateServiceApolloSources
```

Apollo reads the schema and query files and generates Kotlin classes for the operations and their response models.

Generated code is generally located under:

```text
app/build/generated/source/apollo/
```

The exact subdirectory can vary by Apollo version and Gradle configuration.

To list available Gradle tasks:

```powershell
.\gradlew.bat :app:tasks --all
```

To build the debug application:

```powershell
.\gradlew.bat :app:assembleDebug
```

To obtain additional error details when a build fails:

```powershell
.\gradlew.bat :app:assembleDebug --stacktrace
```

## 🧩 Understanding Apollo-Generated Classes

Apollo generates code automatically from GraphQL operations. These files should generally not be edited manually because they can be regenerated during a build.

### Query classes

A query such as `Characters.graphql` generates a Kotlin operation class, for example `CharactersQuery`.

This class represents the GraphQL operation and provides a type-safe way to execute it through Apollo Client.

### Response models

Apollo generates Kotlin models representing the structure of the query response.

These models allow the application to access returned fields using Kotlin properties rather than manually parsing JSON.

### Adapters

Adapters support converting between serialized response data and Kotlin representations of generated types, depending on the configured response format and adapter implementation.

They are part of Apollo's generated data-handling infrastructure. In a typical application, Apollo manages their use, so they do not usually need to be called manually.

### Selections

Selection metadata represents the fields requested by a GraphQL operation and the relationships between selected fields.

Apollo uses this information as part of its generated operation and response-handling implementation.

Developers generally do not need to instantiate or use generated selection-related implementation classes directly.

### Why are these classes generated?

Code generation helps Apollo provide:

* Type-safe query results.
* Earlier detection of invalid fields and query structures.
* Less manual JSON parsing.
* Kotlin models that reflect the selected GraphQL fields.
* A clearer relationship between GraphQL operations and application code.

## 🔄 Application Architecture

The application separates data fetching, state management, and UI presentation.

**Apollo Client → Repository → ViewModel → Jetpack Compose UI**

* **Apollo Client:** Executes GraphQL operations and processes server responses.
* **Repository:** Provides an application-facing data access layer.
* **ViewModel:** Manages screen state and coordinates data loading.
* **Jetpack Compose UI:** Observes state and renders the screen.

This separation makes the code easier to understand, test, and extend.

## 🔐 Internet Permission

The Android application requires internet access to communicate with the GraphQL API.

Add this permission to `AndroidManifest.xml`, outside the `application` element:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 🎯 Learning Goals

This project is intended to help practice:

* Understanding GraphQL queries, fields, arguments, and schemas.
* Configuring Apollo Kotlin in an Android project.
* Generating Kotlin code from GraphQL operations.
* Executing queries and handling responses.
* Managing asynchronous requests with coroutines.
* Observing UI state using StateFlow.
* Connecting a data layer to a Jetpack Compose interface.
* Understanding how generated adapters and selection metadata support Apollo's internals.

## 🔮 Possible Future Improvements

* Display character images using Coil.
* Add a character details screen.
* Implement pagination.
* Add search and filtering.
* Introduce dependency injection.
* Add unit tests for data and state-handling logic.
* Handle network failures and empty responses more gracefully.

