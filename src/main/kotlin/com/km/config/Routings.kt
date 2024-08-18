package com.km.config

import com.km.routes.counter.counterRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {

  install(Routing) {
    counterRoutes()

////        val keyStoreFile = File("build/keystore.jks")
////        val keyStore = buildKeyStore {
////          certificate("sampleAlias") {
////            password = "foobar"
////            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
////          }
////        }
////
////        KeyStore.getInstance("build/keystore.jks", "123456")
////        keyStore.saveToFile(keyStoreFile, "123456")

  }
}