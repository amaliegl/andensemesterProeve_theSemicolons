CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                       email VARCHAR(50) NOT NULL,
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