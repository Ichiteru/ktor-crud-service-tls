package com.km.routes.counter

import com.km.routes.common.RouteResponse.Companion.ok
import com.km.service.DefaultCounterService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.counterRoutes() {

  val counterService = DefaultCounterService()

  route("/counters") {

    get("/{name}") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")
      val read = counterService.read(name)

      if (read == null) {
        call.respond(ok(Unit))
      } else {
        call.respond(ok(read))
      }
    }

    get {
      val allCounters = counterService.getAll()
      call.respond(ok(allCounters))
    }

    delete("/{name}") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")
      counterService.delete(name)

      call.respond(ok(Unit))
    }

    post("/{name}/increment") {
      val name = call.parameters["name"] ?: throw IllegalStateException("Name parameter must be provided")

      val newValue = counterService.incrementAndGetValue(name)

      call.respond(ok(newValue))
    }

    post {
      val request = call.receive<CreateCounterRequest>()

      val id = counterService.create(
        name = request.name,
        value = request.value,
      )

      call.respond(HttpStatusCode.Created, ok(id))
    }
  }

}

@Serializable
data class CreateCounterRequest(
  val name: String,
  val value: Int = 0
)
