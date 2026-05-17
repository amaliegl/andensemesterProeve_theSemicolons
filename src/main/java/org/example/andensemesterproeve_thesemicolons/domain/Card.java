package org.example.andensemesterproeve_thesemicolons.domain;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Rarity_ENUM getRarity() {
        return rarity;
    }

    public String getSet() {
        return set;
    }
}
