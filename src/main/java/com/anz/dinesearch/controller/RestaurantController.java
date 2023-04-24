package com.anz.dinesearch.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anz.dinesearch.exception.RestaurantNotFoundException;
import com.anz.dinesearch.model.Restaurant;
import com.anz.dinesearch.model.Review;
import com.anz.dinesearch.service.impl.RestaurantServiceImpl;
import jakarta.validation.Valid;

/**
 * @author Shamla TK Controller to perform addition, deletion, search and update
 *         operations on restaurant data
 *
 */
@RestController
@RequestMapping("/api/v1")
public class RestaurantController {

	private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

	private final RestaurantServiceImpl restaurantService;

	public RestaurantController(RestaurantServiceImpl restaurantService) {
		this.restaurantService = restaurantService;
	}

	@PostMapping("/restaurants")
	public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
		Restaurant result = restaurantService.addRestaurant(restaurant);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/restaurants")
	public ResponseEntity<?> getAllRestaurants(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {

		log.debug("Page size will be {} by default", 0);

		List<Restaurant> restaurants = new ArrayList<>();
		restaurants = restaurantService.getAllRestaurants(page, size).getContent();
		if (restaurants.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<EntityModel<Restaurant>> resultList = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			EntityModel<Restaurant> entityModel = createReviewLink(restaurant);
			resultList.add(entityModel);
		}
		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

	@GetMapping("/restaurants/{id}")
	public ResponseEntity<?> getRestaurantById(@PathVariable String id) {
		Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(id);
		if (optionalRestaurant.isPresent()) {
			Restaurant restaurant = optionalRestaurant.get();
			EntityModel entityModel = createReviewLink(restaurant);
			return new ResponseEntity<>(entityModel, HttpStatus.OK);
		} else {
			throw new RestaurantNotFoundException("Restaurant with id " + id + " doesnt exist");
		}
	}

	private EntityModel<Restaurant> createReviewLink(Restaurant restaurant) {
		EntityModel<Restaurant> entityModel = EntityModel.of(restaurant);
		WebMvcLinkBuilder link = linkTo(
				methodOn(RestaurantController.class).getRestaurantReviews(restaurant.getName()));
		entityModel.add(link.withRel("reviews"));
		return entityModel;
	}

	@DeleteMapping("/restaurants/{id}")
	public ResponseEntity<HttpStatus> deleteRestaurantById(@PathVariable String id) {
		restaurantService.deleteRestaurantById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/restaurants/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id,
			@Valid @RequestBody Restaurant restaurant) {
		restaurantService.updateRestaurant(id, restaurant);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/*
	 * api to retrieve restaurant information matching cuisine or restaurant name
	 */
	@GetMapping("/search")
	public List<Restaurant> searchByCuisineOrName(@RequestParam("cuisine") String cuisine) {
		return restaurantService.searchByCuisineOrName(cuisine);
	}

	/*
	 * api to retrieve restaurant information matching name and location name
	 */
	@GetMapping("/search/nameandlocation")
	public List<Restaurant> searchByNameAndLocation(@RequestParam String name, @RequestParam String location) {
		return restaurantService.searchByNameAndLocation(name, location);
	}

//	below api uses google places api upon having a valid api key
	@GetMapping("/reviews/{name}")
	public List<Review> getRestaurantReviews(@PathVariable String name) {
		return restaurantService.getGoogleReviews(name);
	}
}
