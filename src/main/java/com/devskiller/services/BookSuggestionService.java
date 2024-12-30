package com.devskiller.services;

import com.devskiller.exceptions.AppError;
import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Genre;
import com.devskiller.model.Reader;

import java.util.Set;
import java.util.stream.Collectors;

class BookSuggestionService {
	private final int DEFAULT_RATING = 4;

	private final Set<Book> books;
	private final Set<Reader> readers;

	public BookSuggestionService(Set<Book> books, Set<Reader> readers) {
		this.books = books;
		this.readers = readers;
	}

	Set<String> suggestBooks(Reader reader) {
		return books.stream()
			.filter(book -> isRatingEqualOrHigherThanDefault(book.rating()))
			.filter(book -> containsGenreInFavourites(reader, book.genre()))
			.filter(book -> isBookInOtherFavouriteList(book, reader))
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, int rating) {
		if (isNotValidRating(rating)) {
			throw new AppError("Rating should be between 1 and 5");
		}

		return books.stream()
			.filter(book -> areEqualRatings(book.rating(), rating))
			.filter(book -> containsGenreInFavourites(reader, book.genre()))
			.filter(book -> isBookInOtherFavouriteList(book, reader))
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, Author author) {
		return books.stream()
			.filter(book -> isRatingEqualOrHigherThanDefault(book.rating()))
			.filter(book -> containsGenreInFavourites(reader, book.genre()))
			.filter(book -> isBookInOtherFavouriteList(book, reader))
			.filter(book -> isBookWrittenBy(book, author))
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	private boolean isBookWrittenBy(Book book, Author author) {
		return book.author().equals(author);
	}

	private boolean containsBookInFavourites(Reader reader, Book book) {
		return reader.favouriteBooks().contains(book);
	}

	private boolean areReadersAgesEqual(Reader reader, Reader anotherReader) {
		return reader.age() == anotherReader.age();
	}

	private boolean isNotSameReader(Reader reader, Reader anotherReader) {
		return !reader.equals(anotherReader);
	}

	private boolean containsGenreInFavourites(Reader reader, Genre genre) {
		return reader.favouriteGenres().contains(genre);
	}

	private boolean isRatingEqualOrHigherThanDefault(int bookRating) {
		return bookRating >= DEFAULT_RATING;
	}

	private boolean isBookInOtherFavouriteList(Book book, Reader reader ) {
		return readers.stream()
			.filter(currentReader -> isNotSameReader(currentReader, reader))
			.filter(currentReader -> areReadersAgesEqual(currentReader, reader))
			.anyMatch(currentReader -> containsBookInFavourites(currentReader, book));
	}

	private boolean areEqualRatings(int bookRating, int rating) {
		return bookRating == rating;
	}

	private boolean isNotValidRating(int rating) {
		return rating < 1 || rating > 5;
	}

}
