package model

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val title: String,
    val author: String,
    val description: String,
    val priority: String,
    val creation: String
)
