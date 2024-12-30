package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Reader;

import java.util.Set;

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
		throw new UnsupportedOperationException(/*TODO*/);
	}

	Set<String> suggestBooks(Reader reader, Author author) {
		throw new UnsupportedOperationException(/*TODO*/);
	}

}
