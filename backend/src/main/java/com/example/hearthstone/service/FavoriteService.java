package com.example.hearthstone.service;

import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.entity.Card;

public interface FavoriteService {
    void add(Long userId, Long cardId);
    void remove(Long userId, Long cardId);
    PageResult<Card> list(Long userId, long page, long size);
    boolean isFavorite(Long userId, Long cardId);
}
