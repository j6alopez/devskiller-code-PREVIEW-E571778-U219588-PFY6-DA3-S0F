package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Genre;
import com.devskiller.model.Reader;

import java.util.Set;
import java.util.stream.Collectors;

class BookSuggestionService {
	private final static int DEFAULT_RATING = 4;

	private final Set<Book> books;
	private final Set<Reader> readers;

	public BookSuggestionService(Set<Book> books, Set<Reader> readers) {
		this.books = books;
		this.readers = readers;
	}

	Set<String> suggestBooks(Reader reader) {
		return books.stream()
			.filter(book -> book.rating() >= DEFAULT_RATING)
			.filter(book -> containsGenreInFavourites(reader, book.genre()))
			.filter(book -> {
				return readers.stream()
					.filter(currentReader -> isNotSameReader(reader, currentReader))
					.filter(currentReader -> areReadersAgesEqual(reader, currentReader))
					.anyMatch(currentReader -> containsBookInFavourites(currentReader, book));
			})
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, int rating) {
		return books.stream()
			.filter(book -> book.rating() == rating)
			.filter(book -> reader.favouriteGenres().contains(book.genre()))
			.filter(book -> {
				return readers.stream()
					.filter(currentReader -> isNotSameReader(reader, currentReader))
					.filter(currentReader -> areReadersAgesEqual(reader, currentReader))
					.anyMatch(currentReader -> containsBookInFavourites(currentReader, book));
			})
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, Author author) {
		throw new UnsupportedOperationException(/*TODO*/);
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

}
