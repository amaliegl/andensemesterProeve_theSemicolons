CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                       email VARCHAR(50) UNIQUE NOT NULL,
                       title ENUM('Spiller', 'Arrangoer', 'Admin')
);

CREATE TABLE cards (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       type ENUM ('Artifact', 'Artifact_Creature', 'Basic_Land', 'Creature', 'Enchantment', 'Enchantment_Creature', 'Instant', 'Kindred_Instant', 'Land', 'Legendary_Creature', 'Legendary_Enchantment_Creature', 'Legendary_Planeswalker', 'Snow_Creature', 'Sorcery') NOT NULL,
                       set_abbreviation VARCHAR(3) NOT NULL,
                       rarity ENUM ('Common', 'Uncommon', 'Rare', 'Mythic_Rare', 'Land') NOT NULL,
                       image_url VARCHAR(120) NOT NULL,
                       reference_url VARCHAR(100) NOT NULL
);

CREATE TABLE decks (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT NOT NULL,
                       format VARCHAR(50),
                       name VARCHAR(50) NOT NULL,

                       CONSTRAINT fk_decks_user_id
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
);

CREATE TABLE user_owned_cards (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  user_id INT NOT NULL,
                                  card_id INT NOT NULL,
                                  for_swapping BOOLEAN NOT NULL,
                                  card_visible BOOLEAN NOT NULL,

                                  CONSTRAINT fk_user_owned_cards_user_id FOREIGN KEY (user_id) REFERENCES users(id),
                                  CONSTRAINT fk_user_owned_cards_card FOREIGN KEY (card_id) REFERENCES cards(id)
);

CREATE TABLE deck_cards (
                            deck_id INT NOT NULL,
                            user_owned_card_id INT NOT NULL,

                            PRIMARY KEY (deck_id, user_owned_card_id),
                            CONSTRAINT fk_deck_cards_deck_id FOREIGN KEY (deck_id) REFERENCES decks(id),
                            CONSTRAINT fk_deck_cards_user_owned_card_id FOREIGN KEY (user_owned_card_id) REFERENCES user_owned_cards(id)
);

CREATE TABLE events (
                        id INT auto_increment PRIMARY KEY,
                        creator_id  INT NOT NULL,
                        event_type ENUM ('Turnering', 'Casual'),
                        format VARCHAR(50),
                        max_players INT NOT NULL,
                        event_date DATE NOT NULL,
                        event_time TIME NOT NULL,
                        event_status ENUM ('Aaben_for_tilmelding', 'Fuldt_booket', 'Lukket_for_tilmelding', 'Afholdt'),
                        CONSTRAINT fk_creator_id FOREIGN KEY (creator_id) REFERENCES users(id)

);

CREATE TABLE event_users(
                            event_id INT NOT NULL,
                            user_id INT NOT NULL,
                            leaderboard_placing INT,

                            PRIMARY KEY (event_id, user_id),
                            CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events(id),
                            CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);