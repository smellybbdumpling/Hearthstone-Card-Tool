package com.example.hearthstone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hearthstone.common.PageResult;
import com.example.hearthstone.dto.CardQueryRequest;
import com.example.hearthstone.entity.Card;
import com.example.hearthstone.exception.BusinessException;
import com.example.hearthstone.mapper.CardMapper;
import com.example.hearthstone.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CardServiceImpl implements CardService {
    private static final String SET_ORDER_SQL = "FIELD(card_set, '核心2026', '治愈艾泽拉斯', '大地的裂变', '永恒回响', '穿越时间流', '重生之日', '安戈洛龟途', '世界之树的余烬', '漫游翡翠梦境')";

    private final CardMapper cardMapper;

    public CardServiceImpl(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    @Override
    public PageResult<Card> query(CardQueryRequest request) {
        LambdaQueryWrapper<Card> wrapper = new LambdaQueryWrapper<Card>()
                .eq(Card::getStandardLegal, true)
                .last("ORDER BY " + SET_ORDER_SQL + ", cost ASC, name_cn ASC");
        if (StringUtils.hasText(request.keyword())) {
            wrapper.and(item -> item.like(Card::getNameCn, request.keyword()).or().like(Card::getNameEn, request.keyword()));
        }
        if (StringUtils.hasText(request.cardSet())) {
            wrapper.eq(Card::getCardSet, request.cardSet());
        }
        if (StringUtils.hasText(request.cardClass())) {
            wrapper.eq(Card::getCardClass, request.cardClass());
        }
        if (StringUtils.hasText(request.rarity())) {
            wrapper.eq(Card::getRarity, request.rarity());
        }
        if (StringUtils.hasText(request.cardType())) {
            wrapper.eq(Card::getCardType, request.cardType());
        }
        if (request.cost() != null) {
            if (request.cost() >= 10) {
                wrapper.ge(Card::getCost, 10);
            } else {
                wrapper.eq(Card::getCost, request.cost());
            }
        }
        Page<Card> page = cardMapper.selectPage(new Page<>(request.safePage(), request.safeSize()), wrapper);
        return new PageResult<>(page.getTotal(), page.getCurrent(), page.getSize(), page.getRecords());
    }

    @Override
    public Card detail(Long id) {
        Card card = cardMapper.selectById(id);
        if (card == null) {
            throw new BusinessException("卡牌不存在");
        }
        return card;
    }
}
