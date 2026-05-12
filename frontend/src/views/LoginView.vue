<template>
  <section class="auth-page">
    <el-form class="auth-panel" label-position="top" @submit.prevent="submit">
      <h1>登录</h1>
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" show-password />
      </el-form-item>
      <el-button type="primary" class="full-button" @click="submit">登录</el-button>
      <el-button link @click="$router.push('/register')">还没有账号，去注册</el-button>
    </el-form>
  </section>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()
const form = reactive({ username: '', password: '' })

async function submit() {
  await auth.login(form.username, form.password)
  ElMessage.success('登录成功')
  await router.push('/')
}
</script>
