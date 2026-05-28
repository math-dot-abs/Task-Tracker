package model

import kotlinx.serialization.Serializable
import org.bson.Document
import java.util.Date
import kotlin.time.Instant
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

@Serializable
data class Task(
    val id: String?,
    val title: String,
    val author: String,
    val description: String,
    val priority: Priority,
    val creation: Instant
) {
    fun toDocument(): Document {
        return Document(
            mapOf<String, Any?>(
                TITLE to title,
                AUTHOR to author,
                DESCRIPTION to description,
                PRIORITY to priority,
                CREATION to Date.from(creation.toJavaInstant()),
            )
        )
    }

    companion object {
        fun fromDocument(doc: Document): Task {
            return Task(
                doc.getObjectId(ID).toHexString(),
                doc.getString(TITLE),
                doc.getString(AUTHOR),
                doc.getString(DESCRIPTION),
                Priority.valueOf(doc.getString(PRIORITY)),
                doc.getDate(CREATION).toInstant().toKotlinInstant()
            )
        }
    }
}

enum class Priority {
    HIGH, MEDIUM, LOW, NONE
}

const val ID = "_id"
const val TITLE = "title"
const val AUTHOR = "author"
const val DESCRIPTION = "description"
const val PRIORITY = "priority"
const val CREATION = "creation"
