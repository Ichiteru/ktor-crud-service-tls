package com.km.db.migration

import com.km.model.Counters
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V00001_Create_counters_table : BaseJavaMigration() {
  override fun migrate(context: Context?) {
    transaction {
      SchemaUtils.create(Counters)

      Counters.insert {
        it[name] = "counter_100"
        it[value] = 100
      }
      Counters.insert {
        it[name] = "counter_200"
        it[value] = 200
      }
      Counters.insert {
        it[name] = "counter_50"
        it[value] = 50
      }
    }
  }
}