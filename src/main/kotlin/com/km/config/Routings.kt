package com.km.config

import com.km.routes.counter.counterRoutes
import com.km.routes.swager.swaggerRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {

  install(Routing) {
    counterRoutes()
    swaggerRoutes()
  }

  install(CORS) {
    anyHost()
    allowHeader(HttpHeaders.ContentType)
    allowHeader(HttpHeaders.AccessControlAllowHeaders)
    allowHeader(HttpHeaders.AccessControlAllowOrigin)
  }
}
