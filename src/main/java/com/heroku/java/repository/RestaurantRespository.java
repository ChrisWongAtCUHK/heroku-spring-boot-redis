package com.heroku.java.repository;

import org.springframework.stereotype.Repository;

import com.heroku.java.model.Restaurant;
import com.redis.om.spring.repository.RedisDocumentRepository;

@Repository
public interface RestaurantRespository extends RedisDocumentRepository<Restaurant, String> {
}
