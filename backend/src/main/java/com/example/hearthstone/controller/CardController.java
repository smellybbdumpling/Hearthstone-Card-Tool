package com.example.hearthstone.controller;

import com.example.hearthstone.common.ApiResponse;
import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.dto.CardQueryRequest;
import com.example.hearthstone.entity.Card;
import com.example.hearthstone.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ApiResponse<PageResult<Card>> query(CardQueryRequest request) {
        return ApiResponse.ok(cardService.query(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<Card> detail(@PathVariable Long id) {
        return ApiResponse.ok(cardService.detail(id));
    }
}
