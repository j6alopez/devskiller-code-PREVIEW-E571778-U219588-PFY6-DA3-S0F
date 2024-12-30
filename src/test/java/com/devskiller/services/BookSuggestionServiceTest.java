package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Genre;
import com.devskiller.model.Reader;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.devskiller.model.Genre.*;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

public class BookSuggestionServiceTest {
    private final Author author1 = createRandomAuthor(8, 10);
    private final Author author2 = createRandomAuthor(8, 10);
    private final Author author3 = createRandomAuthor(8, 10);
    private final Author author4 = createRandomAuthor(8, 10);
    private final Author author5 = createRandomAuthor(8, 10);
    private final Author author6 = createRandomAuthor(8, 10);

    private final Book book1 = createBook(author1, randomAlphabetic(15), HORROR, 5);
    private final Book book2 = createBook(author1, randomAlphabetic(15), HORROR, 4);
    private final Book book3 = createBook(author2, randomAlphabetic(15), HORROR, 3);
    private final Book book4 = createBook(author3, randomAlphabetic(15), ROMANTIC, 5);
    private final Book book5 = createBook(author4, randomAlphabetic(15), HORROR, 5);
    private final Book book6 = createBook(author5, randomAlphabetic(15), DRAMA, 5);
    private final Book book7 = createBook(author1, randomAlphabetic(15), HORROR, 3);
    private final Book book8 = createBook(author1, randomAlphabetic(15), FICTION, 4);
    private final Book book9 = createBook(author1, randomAlphabetic(15), HORROR, 4);
    private final Book book10 = createBook(author6, randomAlphabetic(15), FICTION, 4);

    private final int randomAge1 = nextInt(0, 120);
    private final int randomAge2 = nextInt(0, 120);

    private final Reader reader1 = new Reader(randomAge1);
    private final Reader reader2 = new Reader(randomAge1);
    private final Reader reader3 = new Reader(randomAge2);

    private BookSuggestionService suggestionService;

    @BeforeEach
    public void setUp() {
        reader1.addToFavourites(HORROR);
        reader1.addToFavourites(ROMANTIC);
        reader2.addToFavourites(book2);
        reader2.addToFavourites(book1);
        reader2.addToFavourites(book3);
        reader2.addToFavourites(book4);
        reader2.addToFavourites(book7);
        reader2.addToFavourites(book8);
        reader2.addToFavourites(book10);
        reader3.addToFavourites(book5);
        Set<Book> books = Sets.newHashSet(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10);
        Set<Reader> readers = newHashSet(reader1, reader2, reader3);
        suggestionService = new BookSuggestionService(books, readers);
    }

    @Test
    @DisplayName("Should suggest book titles with correct rating")
    public void shouldSuggestBookTitlesWithCorrectRating() {
        //given:
        int rating = 4;
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1, rating);

        // then:
        assertThat(suggestedBooks).isEqualTo(newHashSet(book2.title()));

    }

    @Test
    @DisplayName("Should suggest book titles with default rating of four or higher")
    public void shouldSuggestBookTitlesWithDefaultRatingOfFourOrHigher() {
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1);

		// then:
		assertThat(suggestedBooks).isEqualTo(newHashSet(book1.title(), book2.title(),
				book4.title()));
	}

    @Test
    @DisplayName("Should only suggest book titles of a given author")
    @Disabled
    public void shouldOnlySuggestBookTitlesOfGivenAuthor() {
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1, author1);

        // then:
        assertThat(suggestedBooks).isEqualTo(newHashSet(book1.title(), book2.title()));
    }

    private Author createRandomAuthor(int firstNameLength, int lastNameLength) {
        return new Author(randomAlphabetic(firstNameLength), randomAlphabetic(lastNameLength));
    }

    private Book createBook(Author author, String title, Genre genre, int rating) {
        return new Book(author, title, randomAlphabetic(10), genre, rating);
    }
}
