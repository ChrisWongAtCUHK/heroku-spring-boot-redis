package com.heroku.java.controller.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.java.model.Restaurant;
import com.heroku.java.repository.RestaurantRespository;
import com.heroku.java.service.IdGeneratorService;

@RestController
@RequestMapping("/redis/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantRespository restaurantRespository;

  @Autowired
  private IdGeneratorService idGeneratorService;

  @GetMapping("")
  public List<Restaurant> getRestaurants(@RequestParam(required = false) String cuisine) {
    List<Restaurant> restaurants = new ArrayList<>();
    if (cuisine != null) {
      Iterable<Restaurant> iterable = restaurantRespository.searchByCuisine(cuisine);

      for (Restaurant restaurant : iterable) {
        restaurants.add(restaurant);
      }

      return restaurants;
    }

    try {
      restaurants = restaurantRespository.findAll();
    } catch (Exception e) {
      Restaurant restaurant = new Restaurant();
      restaurant.setName(e.getMessage());
    }
    return restaurants;
  }

  @PostMapping("")
  public String saveRestaurant(@RequestBody Restaurant restaurant) {
    try {
      restaurant.setId(idGeneratorService.generateId());
      restaurantRespository.save(restaurant);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "";
  }

  @GetMapping("/{id}")
  public Restaurant getRestaurantById(@PathVariable("id") long id) {
    Restaurant restaurant = new Restaurant();
    try {
      return restaurantRespository.findById(String.valueOf(id)).orElse(restaurant);
    } catch (Exception e) {
      restaurant.setName(e.getMessage());
      return restaurant;
    }
  }

  @PostMapping("/{id}")
  public Restaurant updateRestaurantById(@PathVariable("id") long id, @RequestBody Restaurant newRestaurant) {
    Restaurant restaurant = new Restaurant();
    try {
      restaurant = restaurantRespository.findById(String.valueOf(id)).orElse(restaurant);
      restaurant.setName(newRestaurant.getName());
      restaurant.setCuisine(newRestaurant.getCuisine());
      restaurant.setLocation(newRestaurant.getLocation());

      restaurantRespository.save(restaurant);
    } catch (Exception e) {
      restaurant.setName(e.getMessage());
      return restaurant;
    }
    return restaurant;
  }

  @DeleteMapping("/{id}")
  public String deleteRestaurantById(@PathVariable("id") long id) {
    try {
      Restaurant restaurant = new Restaurant();
      restaurant = restaurantRespository.findById(String.valueOf(id)).orElse(restaurant);
      restaurantRespository.delete(restaurant);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Restaurant " + id + " is deleted.";
  }
}
