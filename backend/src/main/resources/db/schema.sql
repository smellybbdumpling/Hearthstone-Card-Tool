-- This schema is part of a personal learning and demonstration project.
-- Hearthstone-related card names, text, images, trademarks, and other game content
-- are owned by their respective rights holders and are not covered by this project's MIT license.

CREATE DATABASE IF NOT EXISTS hearthstone_cards DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hearthstone_cards;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(64) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS cards (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    card_id VARCHAR(64) NOT NULL UNIQUE,
    name_cn VARCHAR(128) NOT NULL,
    name_en VARCHAR(128),
    card_set VARCHAR(64) NOT NULL,
    card_class VARCHAR(32) NOT NULL,
    rarity VARCHAR(32),
    card_type VARCHAR(32),
    spell_school VARCHAR(32),
    cost INT,
    attack INT,
    health INT,
    durability INT,
    description TEXT,
    flavor_text TEXT,
    artist VARCHAR(128),
    image_url VARCHAR(512),
    standard_legal TINYINT(1) NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_cards_query (standard_legal, card_set, card_class, rarity, card_type, cost),
    INDEX idx_cards_name_cn (name_cn),
    INDEX idx_cards_name_en (name_en)
);

CREATE TABLE IF NOT EXISTS user_favorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    UNIQUE KEY uk_user_card (user_id, card_id),
    INDEX idx_favorite_user (user_id, created_at),
    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS card_data_version (
    version INT PRIMARY KEY,
    generated_at DATETIME NOT NULL,
    source_url VARCHAR(512) NOT NULL,
    series_scope VARCHAR(512) NOT NULL,
    total_cards INT NOT NULL,
    checksum VARCHAR(128) NOT NULL
);
