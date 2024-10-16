package core.data.source.remote

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
        supabaseUrl = Constants.SUPABASE_URL,
        supabaseKey = Constants.SUPABASE_KEY
    ) {
        defaultLogLevel = io.github.jan.supabase.logging.LogLevel.DEBUG
        defaultSerializer = KotlinXSerializer(jsonConfiguration)

        install(Postgrest)
    }
}