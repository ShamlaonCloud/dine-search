package com.anz.dinesearch.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.anz.dinesearch.model.Place;
import com.anz.dinesearch.model.Restaurant;
import com.anz.dinesearch.model.Review;
import com.anz.dinesearch.model.Reviews;
import com.anz.dinesearch.repository.RestaurantRepository;
import com.anz.dinesearch.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private static final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	private final RestaurantRepository restaurantRepository;
	private final WebClient webClient;

	@Value("${restaurant.place.uri}")
	private String placeUri;
	@Value("${restaurant.location}")
	private String location;
	@Value("${restaurant.type}")
	private String type;
	@Value("${restaurant.radius}")
	private String radius;
	@Value("${restaurant.review.uri}")
	private String reviewUri;
	@Value("${api.key}")
	private String apiKey;

	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, WebClient webClient) {
		this.restaurantRepository = restaurantRepository;
		this.webClient = webClient;
	}

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Page<Restaurant> getAllRestaurants(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return restaurantRepository.findAll(paging);
	}

	@Override
	public Optional<Restaurant> getRestaurantById(String id) {
		return restaurantRepository.findById(id);
	}

	@Override
	public void deleteRestaurantById(String id) {
		restaurantRepository.deleteById(id);
	}

	@Override
	public Restaurant updateRestaurant(String id, Restaurant restaurant) {
		return restaurantRepository.findById(id).map(existingRestaurant -> {
			existingRestaurant.setName(restaurant.getName());
			existingRestaurant.setCuisine(restaurant.getCuisine());
			existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
			existingRestaurant.setAddress(restaurant.getAddress());
			existingRestaurant.setMenuItems(restaurant.getMenuItems());
			return restaurantRepository.save(existingRestaurant);
		}).orElseGet(() -> {
			return restaurantRepository.save(restaurant);
		});
	}

	@Override
	public List<Restaurant> searchByCuisineOrName(String cuisineOrName) {
		return restaurantRepository.findByCuisineContainingIgnoreCaseOrNameContainingIgnoreCase(cuisineOrName,
				cuisineOrName);
	}

	@Override
	public List<Restaurant> searchByNameAndLocation(String name, String location) {
		return restaurantRepository.findByNameContainingIgnoreCaseAndAddressStreetContainingIgnoreCase(name, location);
	}

	public List<Review> getGoogleReviews(String name) {
		Place place = webClient.get()
				.uri(uriBuilder -> uriBuilder.path(placeUri).queryParam("location", location)
						.queryParam("radius", radius).queryParam("type", type)
						.queryParam("keyword", name).queryParam("key", apiKey).build())
				.retrieve().bodyToMono(Place.class).block();
		String placeId = place.getResults().get(0).getPlace_id();
		Reviews reviewsObj = webClient.get().uri(uriBuilder -> uriBuilder.path(reviewUri)
				.queryParam("place_id", placeId).queryParam("key", apiKey).build()).retrieve().bodyToMono(Reviews.class)
				.block();
		return reviewsObj.getResult().getReviews();
	}

}
