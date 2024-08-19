package com.km.routes.swager

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Route.swaggerRoutes() {

  route("") {
    swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
//      version = "4.15.5"
    }
  }
}