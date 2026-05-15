package org.example.andensemesterproeve_thesemicolons.domain;

import java.util.List;

public class Deck {
    private int id;
    private String name;
    private String format;
    private List<Card> cards;

    public Deck(int id, String name, String format) {
        this.id = id;
        this.name = name;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
