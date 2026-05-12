package com.example.hearthstone.dto;

public record CardQueryRequest(
        String keyword,
        String cardSet,
        String cardClass,
        String rarity,
        String cardType,
        Integer cost,
        Long page,
        Long size
) {
    public long safePage() {
        return page == null || page < 1 ? 1 : page;
    }

    public long safeSize() {
        return size == null || size < 1 || size > 100 ? 20 : size;
    }
}
