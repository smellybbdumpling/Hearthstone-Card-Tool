<template>
  <section class="tool-page">
    <div class="filter-bar">
      <el-input v-model="filters.keyword" clearable placeholder="搜索中文名或英文名" @keyup.enter="loadCards" />
      <el-select v-model="filters.cardSet" clearable placeholder="系列">
        <el-option v-for="item in cardSets" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="filters.cardClass" clearable placeholder="职业">
        <el-option v-for="item in classes" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="filters.rarity" clearable placeholder="稀有度">
        <el-option v-for="item in rarities" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="filters.cardType" clearable placeholder="类型">
        <el-option v-for="item in types" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="filters.cost" clearable placeholder="费用">
        <el-option v-for="item in costs" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button type="primary" @click="loadCards">查询</el-button>
    </div>

    <div class="result-meta">共 {{ total }} 张</div>

    <div v-loading="loading" class="card-grid">
      <article v-for="card in cards" :key="card.id" class="card-tile">
        <div class="card-image" @click="openDetail(card)">
          <img v-if="card.imageUrl" :src="card.imageUrl" :alt="card.nameCn" />
          <span v-else>{{ card.nameCn }}</span>
        </div>
        <div class="card-content">
          <h3>{{ card.nameCn }}</h3>
          <p>{{ card.nameEn }}</p>
          <div class="tags">
            <el-tag size="small">{{ card.cost ?? '-' }}费</el-tag>
            <el-tag size="small" type="success">{{ card.cardClass }}</el-tag>
            <el-tag size="small" type="warning">{{ card.cardType || '-' }}</el-tag>
          </div>
          <div class="card-actions">
            <el-button size="small" @click="openDetail(card)">详情</el-button>
            <el-button size="small" @click="copy(card.nameCn)">复制</el-button>
            <el-button size="small" type="primary" @click="toggleFavorite(card)">收藏</el-button>
          </div>
        </div>
      </article>
    </div>

    <el-pagination
      v-model:current-page="filters.page"
      v-model:page-size="filters.size"
      background
      layout="prev, pager, next, sizes, total"
      :total="total"
      :page-sizes="[15, 30, 45]"
      @change="loadCards"
    />

    <CardDetailDialog v-model="detailVisible" :card="selectedCard" @favorite="toggleFavorite" />
  </section>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { api } from '@/api'
import type { Card } from '@/api/types'
import { useAuthStore } from '@/stores/auth'
import CardDetailDialog from '@/components/CardDetailDialog.vue'

const auth = useAuthStore()
const router = useRouter()
const loading = ref(false)
const cards = ref<Card[]>([])
const total = ref(0)
const detailVisible = ref(false)
const selectedCard = ref<Card | null>(null)
const filters = reactive({ page: 1, size: 15, keyword: '', cardSet: '', cardClass: '', rarity: '', cardType: '', cost: undefined as number | undefined })
const cardSets = ['核心2026', '治愈艾泽拉斯', '大地的裂变', '永恒回响', '穿越时间流', '重生之日', '安戈洛龟途', '世界之树的余烬', '漫游翡翠梦境']
const classes = ['德鲁伊', '猎人', '法师', '圣骑士', '牧师', '潜行者', '萨满', '术士', '战士', '恶魔猎手', '死亡骑士', '中立']
const rarities = ['普通', '稀有', '史诗', '传说']
const types = ['随从', '法术', '武器', '地标', '英雄']
const costs = Array.from({ length: 11 }, (_, index) => ({ label: index === 10 ? '10+' : String(index), value: index }))

async function loadCards() {
  loading.value = true
  try {
    const result = await api.cards({ ...filters, _ts: Date.now() })
    cards.value = result.records
    total.value = result.total
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '卡牌加载失败')
  } finally {
    loading.value = false
  }
}

function openDetail(card: Card) {
  selectedCard.value = card
  detailVisible.value = true
}

async function toggleFavorite(card: Card) {
  if (!auth.isLoggedIn) {
    ElMessage.warning('请先登录')
    await router.push('/login')
    return
  }
  const favored = await api.favoriteStatus(card.id)
  favored ? await api.unfavorite(card.id) : await api.favorite(card.id)
  ElMessage.success(favored ? '已取消收藏' : '已收藏')
}

async function copy(value: string) {
  await navigator.clipboard.writeText(value)
  ElMessage.success('已复制')
}

onMounted(loadCards)
</script>
