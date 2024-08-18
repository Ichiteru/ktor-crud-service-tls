package com.km

import com.km.plugins.*
import com.km.repository.CounterRepository
import com.km.repository.ExposedCounterRepository
import com.km.routes.counter.counterRoutes
import com.km.service.DefaultCounterService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
  embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
    .start(wait = true)
}

fun Application.module() {
  Databases.configureDatabases()

  install(ContentNegotiation) {
    json(Json {
      ignoreUnknownKeys = true
    })
  }

  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respondText { cause.message ?: "Error when trying to send request" }
    }
  }

  install(Routing) {
    counterRoutes()
  }
}
