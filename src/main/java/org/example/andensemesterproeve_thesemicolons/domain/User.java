package org.example.andensemesterproeve_thesemicolons.domain;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Role_ENUM role;
    private List<Card> cards;
    private List<Deck> decks;

    public User() {    }

    public User(int id) {
        this.id = id;
    }

}
