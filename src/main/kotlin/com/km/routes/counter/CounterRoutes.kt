package com.km.routes.counter

import com.km.repository.ExposedCounterRepository
import com.km.service.DefaultCounterService
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.counterRoutes() {

  val counterService = DefaultCounterService(
    counterRepository = ExposedCounterRepository()
  )

  route("/counters") {
    get("/{name}") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")
      val read = counterService.read(name)

      if (read == null) {
        call.respond(HttpStatusCode.NotFound)
      } else {
        call.respond(read)
      }
    }

    get {
      val allCounters = counterService.getAll()
      call.respond(allCounters)
    }

    delete("/{name}") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")
      counterService.delete(name)

      call.respond(HttpStatusCode.OK)
    }

    post("/{name}/increment") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")

      val newValue = counterService.incrementAndGetValue(name)

      call.respond(newValue)
    }

    post {
      val request = call.receive<CreateCounterRequest>()

      val id = counterService.create(
        name = request.name,
        value = request.value,
      )

      call.respond(id)
    }
  }

}

@Serializable
data class CreateCounterRequest(
  val name: String,
  val value: Int = 0
)
