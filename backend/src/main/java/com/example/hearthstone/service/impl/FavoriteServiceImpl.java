package com.example.hearthstone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.entity.Card;
import com.example.hearthstone.entity.UserFavorite;
import com.example.hearthstone.exception.BusinessException;
import com.example.hearthstone.mapper.CardMapper;
import com.example.hearthstone.mapper.UserFavoriteMapper;
import com.example.hearthstone.service.FavoriteService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final UserFavoriteMapper favoriteMapper;
    private final CardMapper cardMapper;

    public FavoriteServiceImpl(UserFavoriteMapper favoriteMapper, CardMapper cardMapper) {
        this.favoriteMapper = favoriteMapper;
        this.cardMapper = cardMapper;
    }

    @Override
    public void add(Long userId, Long cardId) {
        if (cardMapper.selectById(cardId) == null) {
            throw new BusinessException("卡牌不存在");
        }
        if (isFavorite(userId, cardId)) {
            return;
        }
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setCardId(cardId);
        favorite.setCreatedAt(LocalDateTime.now());
        favoriteMapper.insert(favorite);
    }

    @Override
    public void remove(Long userId, Long cardId) {
        favoriteMapper.delete(new LambdaQueryWrapper<UserFavorite>()
                .eq(UserFavorite::getUserId, userId)
                .eq(UserFavorite::getCardId, cardId));
    }

    @Override
    public PageResult<Card> list(Long userId, long page, long size) {
        Page<UserFavorite> favoritePage = favoriteMapper.selectPage(new Page<>(Math.max(page, 1), Math.min(Math.max(size, 1), 100)),
                new LambdaQueryWrapper<UserFavorite>()
                        .eq(UserFavorite::getUserId, userId)
                        .orderByDesc(UserFavorite::getCreatedAt));
        List<Long> cardIds = favoritePage.getRecords().stream().map(UserFavorite::getCardId).toList();
        List<Card> cards = cardIds.isEmpty()
            ? List.of()
            : cardMapper.selectList(new LambdaQueryWrapper<Card>().in(Card::getId, cardIds));
        return new PageResult<>(favoritePage.getTotal(), favoritePage.getCurrent(), favoritePage.getSize(), cards);
    }

    @Override
    public boolean isFavorite(Long userId, Long cardId) {
        return favoriteMapper.selectCount(new LambdaQueryWrapper<UserFavorite>()
                .eq(UserFavorite::getUserId, userId)
                .eq(UserFavorite::getCardId, cardId)) > 0;
    }
}
