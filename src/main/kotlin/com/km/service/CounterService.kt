package com.km.service

import com.km.model.Counter

interface CounterService {

  suspend fun create(name: String, value: Int): Long
  suspend fun read(name: String): Counter?
  suspend fun delete(name: String): Int
  suspend fun incrementAndGetValue(name: String): Int
  suspend fun getAll(): Collection<Counter>
}
