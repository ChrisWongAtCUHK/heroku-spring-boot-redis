package com.heroku.java.controller.redis;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heroku.java.model.Restaurant;
import com.heroku.java.repository.RestaurantRespository;

@RestController
@RequestMapping("/redis/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantRespository restaurantRespository;

  @GetMapping("")
  public List<Restaurant> getRestaurants(@RequestParam(required = false) String cuisine) {
    List<Restaurant> restaurants = new ArrayList<>();
    try {
      // 1. Create a Reader for the file
      Reader reader = Files.newBufferedReader(Paths
          .get("/Users/chriswong/Documents/Java/SpringBoot/heroku-spring-boot-redis/redis/sample_restaurant.json"));

      // 2. Define the target type using TypeToken
      Type listType = new TypeToken<List<Restaurant>>() {
      }.getType();

      // 3. Deserialize into the List
      restaurants = new Gson().fromJson(reader, listType);

      restaurantRespository.saveAll(restaurants);

      // Close reader
      reader.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return restaurants;
  }

  @PostMapping("")
  public String saveRestaurant(@RequestBody Restaurant restaurant) {
    try {
      restaurantRespository.save(restaurant);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "";
  }
}
