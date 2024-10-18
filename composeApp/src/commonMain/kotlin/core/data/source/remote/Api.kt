package core.data.source.remote

import Youtube.composeApp.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

object Api {
    private val jsonConfiguration
        get() = Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }

    internal fun initSupabase() = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        defaultLogLevel = io.github.jan.supabase.logging.LogLevel.INFO
        defaultSerializer = KotlinXSerializer(jsonConfiguration)

        install(Postgrest)
    }
}