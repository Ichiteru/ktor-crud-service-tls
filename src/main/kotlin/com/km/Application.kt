package com.km

import com.km.config.configureDatabase
import com.km.config.configureRoutes
import com.km.config.configureSerialization
import com.km.config.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
  configureDatabase()
  configureSerialization()
  configureStatusPages()
  configureRoutes()
}
