package com.devskiller.model;

import com.devskiller.exceptions.AppError;

public record Book(Author author, String title, String isbn, Genre genre, int rating) {

	public Book {
		validate(rating);
	}

	private void validate(int rating) {
		if (rating > 5 || rating < 1) {
			throw new AppError("Rating must be between 1 and 5");
		}
	}
}
