package core.data.source.remote

import core.domain.model.Users
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class YoutubeService : Api() {
    suspend fun getUsers(): List<Users> {
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
        return supabase.from("users").select(columns = column) {
            order(column = "created_at", order = Order.DESCENDING, referencedTable = "transactions")
        }.decodeList<Users>()
    }
}