package core.data.source.remote

import core.data.source.YoutubeSource
import core.domain.model.Users
import core.util.utility.Result
import core.util.utility.SupabaseError
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.SupabaseEncodingException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class YoutubeRemoteSource(
    private val client: SupabaseClient
) : YoutubeSource.Remote {
    override suspend fun getUsers(): Result<List<Users>, SupabaseError> {
        return try {
            val column = Columns.raw("""
                id,
                role,
                name,
                transactions(
                    id,
                    note,
                    amount,
                    created_at
                )   
            """.trimIndent())

            val response = client.from("users").select(columns = column) {
                order(column = "created_at", order = Order.DESCENDING, referencedTable = "transactions")
            }.decodeList<Users>()
            Result.Success(response)

        } catch (e: SupabaseEncodingException) {
            Result.Error(SupabaseError.ENCODING)
        } catch (e: Exception) {
            Result.Error(SupabaseError.UNKNOWN)
        }
    }
}