package com.anz.dinesearch.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author Shamla TK entity class contains validations to check null values and
 *         pattern for phone number, zipcode etc 
 *
 */
@Document(collection = "restaurants")
public class Restaurant {
	@Id
	private String id;
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Cuisine type required")
	private String cuisine;
	@Valid
	private Address address;

//	Sample numbers matching the regex : +918880344456, +91 8880344456, 08880344456, 918880344456, +91-8880344456
	@Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", message = "Phone number should be in the following format")
	private String phoneNumber;
	@Valid
	private List<MenuItem> menuItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

}
