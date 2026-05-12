package com.example.hearthstone.controller;

import com.example.hearthstone.common.ApiResponse;
import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.entity.Card;
import com.example.hearthstone.security.AuthUser;
import com.example.hearthstone.service.FavoriteService;
import com.example.hearthstone.vo.FavoriteStatusVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{cardId}")
    public ApiResponse<Void> add(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long cardId) {
        favoriteService.add(authUser.id(), cardId);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{cardId}")
    public ApiResponse<Void> remove(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long cardId) {
        favoriteService.remove(authUser.id(), cardId);
        return ApiResponse.ok();
    }

    @GetMapping
    public ApiResponse<PageResult<Card>> list(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "20") long size
    ) {
        return ApiResponse.ok(favoriteService.list(authUser.id(), page, size));
    }

    @GetMapping("/{cardId}/status")
    public ApiResponse<FavoriteStatusVO> status(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long cardId) {
        return ApiResponse.ok(new FavoriteStatusVO(favoriteService.isFavorite(authUser.id(), cardId)));
    }
}
