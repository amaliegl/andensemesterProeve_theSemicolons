package org.example.andensemesterproeve_thesemicolons;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Title_ENUM;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AndensemesterProeveTheSemicolonsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void newUserHasExpectedValues() {
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        assertEquals(1, testUser.getId());
        assertEquals("testUser", testUser.getUsername());
        assertEquals("testUser@email.com", testUser.getEmail());
        assertEquals(Title_ENUM.Admin, testUser.getTitle());
    }

    @Test
    void validatingInvalidPasswordThrowsException() {
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        testUser.setPassword("abc");
        assertThrows(IllegalArgumentException.class,
                testUser::validateValues);
    }

    @Test
    void newDeckHasExpectedValues() {
        Deck deck = new Deck(1, "Mit deck-navn", "Standard");
        assertEquals(1, deck.getId());
        assertEquals("Mit deck-navn", deck.getName());
        assertEquals("Standard", deck.getFormat());
    }

    @Test
    void settingDeckNameToBlankWillSetDefaultValue() {
        Deck deck = new Deck();
        deck.setName("    ");
        assertEquals("Intet navn", deck.getName());
    }

    @Test
    void settingDeckFormatToBlankWillSetDefaultValueNull() {
        Deck deck = new Deck();
        deck.setFormat("   ");
        assertNull(deck.getFormat());
    }

    @Test
    void addingCardToDeckAddsToDeck() {
        Deck deck = new Deck();
        Card card = new Card();
        deck.addCard(card);
        assertEquals(1, deck.getCards().size());
    }
}
