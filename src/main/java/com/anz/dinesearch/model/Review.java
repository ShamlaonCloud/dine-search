package com.anz.dinesearch.model;

/**
 * @author Shamla TK entity to handle google review. only 3 main attributes are
 *         populated as of now
 *
 */
public class Review {

	private String author_name;
	private String author_url;
	private String rating;

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_url() {
		return author_url;
	}

	public void setAuthor_url(String author_url) {
		this.author_url = author_url;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
