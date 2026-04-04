package com.heroku.java.service;

import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {
  private final RedisAtomicLong counter;

  public IdGeneratorService(RedisConnectionFactory factory) {
    // Initialize or connect to a specific key for person IDs
    this.counter = new RedisAtomicLong("sample_restaurant_id_counter", factory);
  }

  public long generateId() {
    return counter.incrementAndGet(); // Returns the next numeric ID
  }
}
