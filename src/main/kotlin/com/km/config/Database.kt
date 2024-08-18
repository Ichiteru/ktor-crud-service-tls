package com.km.config

import com.km.repository.entity.Counters
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
  val driver = environment.config.property("ktor.db.driverClassName").getString()
  val url = environment.config.property("ktor.db.jdbcUrl").getString()
  val maximumPoolSize = environment.config.property("ktor.db.maximumPoolSize").getString().toInt()
  val isAutoCommit = environment.config.property("ktor.db.isAutoCommit").getString().toBoolean()
  val transactionIsolation = environment.config.property("ktor.db.transactionIsolation").getString()

  val config = HikariConfig().apply {
    this.driverClassName = driver
    this.jdbcUrl = url
    this.maximumPoolSize = maximumPoolSize
    this.isAutoCommit = isAutoCommit
    this.transactionIsolation = transactionIsolation
    validate()
  }

  Database.connect(HikariDataSource(config))

  transaction {
    SchemaUtils.create(Counters)
  }
}

suspend fun <T> dbCall(
  block: () -> T
): T = withContext(Dispatchers.IO) {
  transaction { block() }
}

//private fun runFlyway(datasource: DataSource) {
//  val flyway = Flyway.configure().dataSource(datasource).load()
//  try {
//    flyway.info()
//    flyway.migrate()
//  } catch (e: Exception) {
//    log.error("Exception running flyway migration", e)
//    throw e
//  }
//  log.info("Flyway migration has finished")
//}
