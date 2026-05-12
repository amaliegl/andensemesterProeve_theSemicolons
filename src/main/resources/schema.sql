CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       role ENUM('Spiller', 'Arrangoer', 'Admin')
);

CREATE TABLE cards (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       type ENUM ('Artifact', 'Artifact Creature', 'Basic Land', 'Creature', 'Enchantment', 'Enchantment Creature', 'Instant', 'Kindred Instant', 'Land', 'Legendary Creature', 'Legendary Enchantment Creature', 'Legendary Planeswalker', 'Snow Creature', 'Sorcery') NOT NULL,
                       set VARCHAR(3) NOT NULL,
                       rarity ENUM ('Common', 'Uncommon', 'Rare', 'Mythic Rare', 'Land') NOT NULL,
                       image_url VARCHAR(120) NOT NULL,
                       reference_url VARCHAR(100) NOT NULL
);