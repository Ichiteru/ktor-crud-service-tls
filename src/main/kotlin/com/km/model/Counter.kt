package com.km.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Counter(
  val id : Long? = null,
  val name: String,
  val value: Int
)
