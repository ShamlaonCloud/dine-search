package com.anz.dinesearch.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.anz.dinesearch.model.Restaurant;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

	List<Restaurant> findByNameContainingIgnoreCaseAndAddressStreetContainingIgnoreCase(String name, String location);

	List<Restaurant> findByCuisineContainingIgnoreCaseOrNameContainingIgnoreCase(String cuisine, String name);

}
