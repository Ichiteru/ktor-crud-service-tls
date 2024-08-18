package com.km.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Counters : Table() {
  val id = long("id").autoIncrement()
  val name = varchar("name", 255).uniqueIndex(customIndexName = "counter_name_uix")
  val value = integer("value")
  override val primaryKey: PrimaryKey = PrimaryKey(id)
}

@Serializable
data class Counter(
  val id : Long? = null,
  val name: String,
  val value: Int
)
