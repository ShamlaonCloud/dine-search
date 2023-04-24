package com.anz.dinesearch.model;

import java.util.List;

/**
 * @author Shamla TK entity class to handle google api result
 *
 */
public class GooglePlace {

	private List<Review> reviews;

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

}
