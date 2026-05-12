<template>
  <el-container class="app-shell">
    <el-header class="topbar">
      <router-link class="brand" to="/">炉石标准卡牌库</router-link>
      <nav class="nav-links">
        <router-link to="/">查询</router-link>
        <router-link to="/favorites">收藏</router-link>
      </nav>
      <div class="account">
        <template v-if="auth.isLoggedIn">
          <span>{{ auth.user?.nickname }}</span>
          <el-button size="small" @click="auth.logout()">退出</el-button>
        </template>
        <template v-else>
          <el-button size="small" @click="$router.push('/login')">登录</el-button>
        </template>
      </div>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
</script>
