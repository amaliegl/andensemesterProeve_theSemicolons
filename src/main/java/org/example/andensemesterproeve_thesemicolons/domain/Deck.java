package org.example.andensemesterproeve_thesemicolons.domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private int id;
    private String name;
    private String format;
    private final List<Card> cards = new ArrayList<>();

    public Deck() {}

    public Deck(int id, String name, String format) {
        this.id = id;
        this.name = name;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void setName(String name) {
        if (name.isBlank()) {
            this.name = "Intet navn";
        } else {
            this.name = name;
        }
    }

    public void setFormat(String format) {
        if (format.isBlank()) {
            this.format = null;
        } else {
            this.format = format;
        }
    }
}
