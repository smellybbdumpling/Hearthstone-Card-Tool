<template>
  <section class="auth-page">
    <el-form class="auth-panel" label-position="top" @submit.prevent="submit">
      <div class="auth-panel-heading">
        <router-link class="auth-brand" to="/">炉石标准卡牌库</router-link>
        <h1>登录</h1>
      </div>
      <el-form-item label="用户名">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" show-password />
      </el-form-item>
      <el-button type="primary" class="full-button" :icon="UserFilled" :loading="submitting" @click="submit">登录</el-button>
      <el-button link @click="$router.push('/register')">还没有账号，去注册</el-button>
    </el-form>
  </section>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { UserFilled } from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()
const form = reactive({ username: '', password: '' })
const submitting = ref(false)

async function submit() {
  if (submitting.value) {
    return
  }

  const username = form.username.trim()
  const password = form.password.trim()

  if (!username) {
    ElMessage.warning('请输入用户名')
    return
  }
  if (!password) {
    ElMessage.warning('请输入密码')
    return
  }

  submitting.value = true
  try {
    await auth.login(username, password)
    ElMessage.success('登录成功')
    await router.push('/')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '登录失败')
  } finally {
    submitting.value = false
  }
}
</script>
