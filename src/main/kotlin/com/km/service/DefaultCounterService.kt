package com.km.service

import com.km.model.Counter
import com.km.repository.CounterRepository

class DefaultCounterService(
  private val counterRepository: CounterRepository,
) : CounterService {

  override suspend fun create(name: String, value: Int): Long {
    val counter = Counter(
      name = name,
      value = value
    )
    return counterRepository.save(counter)
  }

  override suspend fun read(name: String): Counter? {
    return counterRepository.getByName(name)
  }

  override suspend fun delete(name: String): Int {
    return counterRepository.deleteByName(name)
  }

  override suspend fun incrementAndGetValue(name: String): Int {
    val counter = counterRepository.getByName(name)
      ?: throw IllegalArgumentException("No counter with name = $name")

    val newValue = counter.value.plus(1)

    return counterRepository.updateValueByName(counter.name, newValue)
  }

  override suspend fun getAll(): Collection<Counter> {
    return counterRepository.getAll()
  }
}
