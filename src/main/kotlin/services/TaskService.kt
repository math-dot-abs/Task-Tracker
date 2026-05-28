package services

import bootstrap.getDatabase
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import model.Priority
import model.Task
import model.Task.Companion.fromDocument
import model.TaskRequest
import kotlin.time.Instant

const val COLLECTION = "Task"

class TaskService(val db: MongoDatabase = getDatabase()) {
    fun getAllTasks(): List<Task> {
        return db.getCollection(COLLECTION)
            .find()
            .map { fromDocument(it) }
            .toList()
    }

    fun createTask(body: TaskRequest): Task {
        val task = convertToTask(body)
        val collection = db.getCollection(COLLECTION)
        val id = collection.insertOne(task.toDocument()).insertedId?.asObjectId()?.value
        return fromDocument(collection.find(eq("_id", id)).first()!!)
    }

    private fun convertToTask(body: TaskRequest): Task {
        return Task(
            null,
            body.title,
            body.author,
            body.description,
            Priority.valueOf(body.priority),
            Instant.parse(body.creation)
        )
    }
}