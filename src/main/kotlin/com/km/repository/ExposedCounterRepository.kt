package com.km.repository

import com.km.config.dbCall
import com.km.model.Counter
import com.km.repository.entity.Counters
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class ExposedCounterRepository : CounterRepository {

  override suspend fun save(counter: Counter): Long = dbCall {
    Counters.insert {
      it[name] = counter.name
      it[value] = counter.value
    } get Counters.id
  }

  override suspend fun getByName(name: String): Counter? = dbCall {
    Counters.selectAll()
      .where { Counters.name eq name }
      .map { toCounter(it) }
      .singleOrNull()
  }

  override suspend fun getValueByName(name: String): Int = dbCall {
    Counters.selectAll()
      .where { Counters.name eq name }
      .map { it[Counters.value] }
      .first()
  }

  override suspend fun deleteByName(name: String): Int = dbCall {
    Counters.deleteWhere { Counters.name eq name }
  }

  override suspend fun updateValueByName(name: String, value: Int): Int = dbCall {
    Counters.update({ Counters.name eq name }) {
      it[Counters.value] = value
    }
  }

  override suspend fun existsByName(name: String) = dbCall {
    Counters.selectAll()
      .where { Counters.name eq name }
      .count() > 0
  }

  override suspend fun getAll(): Collection<Counter> = dbCall {
    Counters.selectAll()
      .map { toCounter(it) }
  }

  private fun toCounter(row: ResultRow) = Counter(
    id = row[Counters.id],
    name = row[Counters.name],
    value = row[Counters.value]
  )
}
