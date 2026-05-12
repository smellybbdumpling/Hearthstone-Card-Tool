package com.example.hearthstone.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@TableName("cards")
public class Card {
    private Long id;
    private String cardId;
    private String nameCn;
    private String nameEn;
    private String cardSet;
    private String cardClass;
    private String rarity;
    private String cardType;
    private String spellSchool;
    private Integer cost;
    private Integer attack;
    private Integer health;
    private Integer durability;
    private String description;
    private String flavorText;
    private String artist;
    private String imageUrl;
    private Boolean standardLegal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public String getNameCn() { return nameCn; }
    public void setNameCn(String nameCn) { this.nameCn = nameCn; }
    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }
    public String getCardSet() { return cardSet; }
    public void setCardSet(String cardSet) { this.cardSet = cardSet; }
    public String getCardClass() { return cardClass; }
    public void setCardClass(String cardClass) { this.cardClass = cardClass; }
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public String getSpellSchool() { return spellSchool; }
    public void setSpellSchool(String spellSchool) { this.spellSchool = spellSchool; }
    public Integer getCost() { return cost; }
    public void setCost(Integer cost) { this.cost = cost; }
    public Integer getAttack() { return attack; }
    public void setAttack(Integer attack) { this.attack = attack; }
    public Integer getHealth() { return health; }
    public void setHealth(Integer health) { this.health = health; }
    public Integer getDurability() { return durability; }
    public void setDurability(Integer durability) { this.durability = durability; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Boolean getStandardLegal() { return standardLegal; }
    public void setStandardLegal(Boolean standardLegal) { this.standardLegal = standardLegal; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
