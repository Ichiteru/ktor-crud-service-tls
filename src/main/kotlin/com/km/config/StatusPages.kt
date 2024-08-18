package com.km.config

import com.km.routes.common.RouteResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respond(
        HttpStatusCode.BadRequest,
        RouteResponse<Unit>(
          errorMessage = cause.message ?: "Error when trying to send request"
        ),
      )
    }
  }
}
