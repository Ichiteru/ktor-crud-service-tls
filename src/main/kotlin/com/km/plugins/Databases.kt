package com.km.plugins

import com.km.model.Counters
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object Databases {

  private val log = LoggerFactory.getLogger(this::class.java)

  fun configureDatabases() {
    val pool = hikari()
    Database.connect(pool)
    transaction {
      SchemaUtils.create(Counters)
    }
//    runFlyway(pool)
  }

  private fun hikari() : HikariDataSource {
    val config = HikariConfig().apply {
      driverClassName = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:mem:test"
      maximumPoolSize = 5
      isAutoCommit = false
      transactionIsolation = "TRANSACTION_REPEATABLE_READ"
      validate()
    }

    return HikariDataSource(config)
  }

  private fun runFlyway(datasource: DataSource) {
    val flyway = Flyway.configure().dataSource(datasource).load()
    try {
      flyway.info()
      flyway.migrate()
    } catch (e: Exception) {
      log.error("Exception running flyway migration", e)
      throw e
    }
    log.info("Flyway migration has finished")
  }

}
