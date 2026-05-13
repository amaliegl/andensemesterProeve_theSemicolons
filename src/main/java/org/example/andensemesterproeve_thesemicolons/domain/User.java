package org.example.andensemesterproeve_thesemicolons.domain;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Title_ENUM title;
    private List<Card> cards;
    private List<Deck> decks;

    public User() {    }

    public User(int id, String username, String email, Title_ENUM title) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
