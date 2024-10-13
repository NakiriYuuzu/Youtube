package core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id: String,
    val role: Int,
    val name: String,
    val transactions: List<Transactions>
)
