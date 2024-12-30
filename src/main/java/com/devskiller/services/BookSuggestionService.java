package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Reader;

import java.util.Set;
import java.util.stream.Collectors;

class BookSuggestionService {

	private final Set<Book> books;
	private final Set<Reader> readers;

	public BookSuggestionService(Set<Book> books, Set<Reader> readers) {
		this.books = books;
		this.readers = readers;
	}

	Set<String> suggestBooks(Reader reader) {
		throw new UnsupportedOperationException(/*TODO*/);
	}

	Set<String> suggestBooks(Reader reader, int rating) {
		return books.stream()
			.filter(book -> book.rating() == rating)
			.filter(book -> reader.favouriteGenres().contains(book.genre()))
			.filter(book -> {
                return readers.stream()
                    .filter(anotherReader -> !anotherReader.equals(reader))
                    .filter(anotherReader -> anotherReader.age() == reader.age())
                    .anyMatch(anotherReader -> anotherReader.favouriteBooks().contains(book));
			})
			.map(Book::title)
			.collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, Author author) {
		throw new UnsupportedOperationException(/*TODO*/);
	}


}
