package com.km.service

import com.km.model.Counter
import com.km.repository.CounterRepository
import com.km.repository.ExposedCounterRepository

class DefaultCounterService(
  private val counterRepository: CounterRepository = ExposedCounterRepository(),
) : CounterService {

  override suspend fun create(name: String, value: Int): Long {
    val counter = Counter(
      name = name,
      value = value
    )

    val existsByName = counterRepository.existsByName(name)

    if (existsByName) {
      throw IllegalArgumentException("Counter with name = $name already exists")
    }

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

    counterRepository.updateValueByName(counter.name, newValue)

    return counterRepository.getValueByName(name)
  }

  override suspend fun getAll(): Collection<Counter> {
    return counterRepository.getAll()
  }
}
