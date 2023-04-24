package com.anz.dinesearch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import com.anz.dinesearch.model.Restaurant;
import com.anz.dinesearch.model.Review;

public interface RestaurantService {

	public Restaurant addRestaurant(Restaurant restaurant);

	public Page<Restaurant> getAllRestaurants(int page, int size);

	public Optional<Restaurant> getRestaurantById(String id);

	public void deleteRestaurantById(String id);

	public Restaurant updateRestaurant(String id, Restaurant restaurant);

	public List<Restaurant> searchByCuisineOrName(String cuisineOrName);

	public List<Restaurant> searchByNameAndLocation(String name, String location);

	public List<Review> getGoogleReviews(String restaurant);

}
