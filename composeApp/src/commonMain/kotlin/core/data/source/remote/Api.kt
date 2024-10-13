package core.data.source.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class Api {
    val client = httpClient
    val supabase = supabaseClient

    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom("")
            path(path)
        }
    }
}

private val jsonConfiguration
    get() = Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(jsonConfiguration)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}

private val supabaseClient = createSupabaseClient(
    supabaseUrl = Constants.SUPABASE_URL,
    supabaseKey = Constants.SUPABASE_KEY
) {
    defaultLogLevel = io.github.jan.supabase.logging.LogLevel.DEBUG
    defaultSerializer = KotlinXSerializer(jsonConfiguration)

    install(Postgrest)
}