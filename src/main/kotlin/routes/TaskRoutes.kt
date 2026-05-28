package routes

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.reflect.TypeInfo
import model.Task
import model.TaskRequest
import services.TaskService

fun Application.configureRouting() {
    routing {
        get("/") {
            val service = TaskService()
            val result = service.getAllTasks()
            call.respond(result, TypeInfo(List::class))
        }
        post("/create") {
            val body = call.receive<TaskRequest>()
            val service = TaskService()
            val result = service.createTask(body)
            call.respond(result, TypeInfo(Task::class))
        }
    }
}