<template>
  <section class="auth-page">
    <el-form class="auth-panel" label-position="top" @submit.prevent="submit">
      <h1>注册</h1>
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" show-password />
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.confirm" type="password" show-password />
      </el-form-item>
      <el-button type="primary" class="full-button" @click="submit">注册并登录</el-button>
      <el-button link @click="$router.push('/login')">已有账号，去登录</el-button>
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
const form = reactive({ username: '', nickname: '', password: '', confirm: '' })

async function submit() {
  if (form.password !== form.confirm) {
    ElMessage.warning('两次密码不一致')
    return
  }
  await auth.register(form.username, form.password, form.nickname)
  ElMessage.success('注册成功')
  await router.push('/')
}
</script>
