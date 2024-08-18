package com.km.repository

import com.km.model.Counter
import com.km.model.Counters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
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

  override suspend fun deleteByName(name: String): Int = dbCall {
    Counters.deleteWhere { Counters.name eq name }
  }

  override suspend fun updateValueByName(name: String, value: Int): Int = dbCall {
    Counters.update({ Counters.name eq name }) {
      it[Counters.value] = value
    }
  }

  override suspend fun getAll(): Collection<Counter> = dbCall {
    Counters.selectAll()
      .map { toCounter(it) }
  }

  private fun toCounter(it: ResultRow) = Counter(
    id = it[Counters.id],
    name = it[Counters.name],
    value = it[Counters.value]
  )

  private suspend fun <T> dbCall(
    block: () -> T
  ): T = withContext(Dispatchers.IO) {
    transaction { block() }
  }
}
