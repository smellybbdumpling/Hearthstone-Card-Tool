<template>
  <el-dialog v-model="visible" width="720px" class="detail-dialog">
    <template #header>
      <div class="detail-title">
        <span>{{ card?.nameCn }}</span>
        <small>{{ card?.nameEn }}</small>
      </div>
    </template>
    <div v-if="card" class="detail-body">
      <div class="image-frame">
        <img v-if="card.imageUrl" :src="card.imageUrl" :alt="card.nameCn" />
        <div v-else class="image-placeholder">{{ card.nameCn }}</div>
      </div>
      <div class="detail-info">
        <div v-if="card.artist" class="artist-line">画家：{{ card.artist }}</div>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="系列">{{ card.cardSet }}</el-descriptions-item>
          <el-descriptions-item label="职业">{{ card.cardClass }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ card.cardType || '-' }}</el-descriptions-item>
          <el-descriptions-item label="派系/种族">{{ card.spellSchool || '-' }}</el-descriptions-item>
          <el-descriptions-item label="费用">{{ card.cost ?? '-' }}</el-descriptions-item>
          <el-descriptions-item label="稀有度">{{ card.rarity || '-' }}</el-descriptions-item>
          <el-descriptions-item label="攻击">{{ card.attack ?? '-' }}</el-descriptions-item>
          <el-descriptions-item label="生命/耐久">{{ card.health ?? card.durability ?? '-' }}</el-descriptions-item>
        </el-descriptions>
        <p class="description">{{ card.description || '暂无描述' }}</p>
        <p v-if="card.flavorText" class="flavor-text">{{ card.flavorText }}</p>
        <div class="dialog-actions">
          <el-button :icon="CopyDocument" @click="copy(card.nameCn)">复制中文名</el-button>
          <el-button :icon="CopyDocument" @click="copy(card.nameEn || card.nameCn)">复制英文名</el-button>
          <el-button type="primary" :icon="Star" @click="$emit('favorite', card)">收藏/取消收藏</el-button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import { CopyDocument, Star } from '@element-plus/icons-vue'
import type { Card } from '@/api/types'

const props = defineProps<{ modelValue: boolean; card: Card | null }>()
const emit = defineEmits<{ 'update:modelValue': [value: boolean]; favorite: [card: Card] }>()

const visible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

async function copy(value: string) {
  await navigator.clipboard.writeText(value)
  ElMessage.success('已复制')
}
</script>
