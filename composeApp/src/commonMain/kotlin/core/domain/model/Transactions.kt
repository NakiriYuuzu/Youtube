package core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transactions(
    val id: String,
    val note: String,
    val amount: Float,
    @SerialName("created_at")
    val createdAt: String
)
