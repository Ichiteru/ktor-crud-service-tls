package com.km.repository

import com.km.model.Counter
import kotlinx.coroutines.flow.Flow

interface CounterRepository {

  suspend fun save(counter: Counter) : Long
  suspend fun getByName(name: String) : Counter?
  suspend fun deleteByName(name: String) : Int
  suspend fun getAll() : Collection<Counter>
  suspend fun updateValueByName(name: String, value: Int): Int
}