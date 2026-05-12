<template>
  <section class="tool-page">
    <div class="page-heading">
      <h1>我的收藏</h1>
      <span>共 {{ total }} 张</span>
    </div>

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
            <el-tag size="small">{{ card.cardClass }}</el-tag>
            <el-tag size="small" type="warning">{{ card.cardType || '-' }}</el-tag>
          </div>
          <div class="card-actions">
            <el-button size="small" @click="openDetail(card)">详情</el-button>
            <el-button size="small" type="danger" @click="remove(card)">取消收藏</el-button>
          </div>
        </div>
      </article>
    </div>

    <el-pagination
      v-model:current-page="page"
      v-model:page-size="size"
      background
      layout="prev, pager, next, sizes, total"
      :total="total"
      :page-sizes="[15, 30, 45]"
      @change="loadFavorites"
    />

    <CardDetailDialog v-model="detailVisible" :card="selectedCard" @favorite="remove" />
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { api } from '@/api'
import type { Card } from '@/api/types'
import CardDetailDialog from '@/components/CardDetailDialog.vue'

const loading = ref(false)
const cards = ref<Card[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(15)
const detailVisible = ref(false)
const selectedCard = ref<Card | null>(null)

async function loadFavorites() {
  loading.value = true
  try {
    const result = await api.favorites({ page: page.value, size: size.value })
    cards.value = result.records
    total.value = result.total
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '收藏加载失败')
  } finally {
    loading.value = false
  }
}

function openDetail(card: Card) {
  selectedCard.value = card
  detailVisible.value = true
}

async function remove(card: Card) {
  await api.unfavorite(card.id)
  ElMessage.success('已取消收藏')
  await loadFavorites()
}

onMounted(loadFavorites)
</script>
