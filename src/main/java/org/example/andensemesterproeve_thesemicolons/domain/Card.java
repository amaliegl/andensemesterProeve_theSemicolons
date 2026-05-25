package org.example.andensemesterproeve_thesemicolons.domain;

import org.example.andensemesterproeve_thesemicolons.domain.enums.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Rarity_ENUM;

public class Card {
    private int id;
    private String name;
    private CardType_ENUM type;
    private String set;
    private Rarity_ENUM rarity;
    private String imageUrl;
    private String referenceUrl;
    private boolean forSwapping;
    private boolean visible;

    public Card() {}

    public Card(int id, String name, CardType_ENUM type, String set, Rarity_ENUM rarity,
                String imageUrl, String referenceUrl, boolean forSwapping,
                boolean visible) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.set = set;
        this.rarity = rarity;
        this.imageUrl = imageUrl;
        this.referenceUrl = referenceUrl;
        this.forSwapping = forSwapping;
        this.visible = visible;
    }

    public Card(int id, String name, CardType_ENUM type, String set, Rarity_ENUM rarity,
                String imageUrl, String referenceUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.set = set;
        this.rarity = rarity;
        this.imageUrl = imageUrl;
        this.referenceUrl = referenceUrl;
    }

    public int getId() { return id;}

    public String getName() { return name;}

    public CardType_ENUM getType() {return type;}

    public String getSet() {return set;}

    public Rarity_ENUM getRarity() { return rarity;}

    public String getImageUrl() { return imageUrl;}

    public String getReferenceUrl() { return referenceUrl;}

    public boolean isForSwapping() {return forSwapping;}

    public boolean isVisible() {return visible;}

    public void setId(int id) {
        this.id = id;
    }

    public void setForSwapping(boolean forSwapping) {
        this.forSwapping = forSwapping;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
