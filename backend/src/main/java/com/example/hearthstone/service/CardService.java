package com.example.hearthstone.service;

import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.dto.CardQueryRequest;
import com.example.hearthstone.entity.Card;

public interface CardService {
    PageResult<Card> query(CardQueryRequest request);
    Card detail(Long id);
}
