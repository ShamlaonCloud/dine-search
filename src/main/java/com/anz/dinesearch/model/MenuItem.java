package com.anz.dinesearch.model;

import jakarta.validation.constraints.NotBlank;

public class MenuItem {
	@NotBlank(message = "Menu name is mandatory")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Price cannot be blank")
	private String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
