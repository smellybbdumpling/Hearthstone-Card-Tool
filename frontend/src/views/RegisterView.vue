<template>
  <section class="auth-page">
    <el-form class="auth-panel" label-position="top" @submit.prevent="submit">
      <div class="auth-panel-heading">
        <router-link class="auth-brand" to="/">炉石标准卡牌库</router-link>
        <h1>注册</h1>
      </div>
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
      <el-button type="primary" class="full-button" :icon="UserFilled" :loading="submitting" @click="submit">注册并登录</el-button>
      <el-button link @click="$router.push('/login')">已有账号，去登录</el-button>
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
const form = reactive({ username: '', nickname: '', password: '', confirm: '' })
const submitting = ref(false)

async function submit() {
  if (submitting.value) {
    return
  }

  const username = form.username.trim()
  const nickname = form.nickname.trim() || username
  const password = form.password.trim()
  const confirm = form.confirm.trim()

  if (!username) {
    ElMessage.warning('请输入用户名')
    return
  }
  if (!password) {
    ElMessage.warning('请输入密码')
    return
  }
  if (password.length < 6) {
    ElMessage.warning('密码至少需要 6 位')
    return
  }
  if (!confirm) {
    ElMessage.warning('请确认密码')
    return
  }
  if (password !== confirm) {
    ElMessage.warning('两次密码不一致')
    return
  }

  submitting.value = true
  try {
    await auth.register(username, password, nickname)
    ElMessage.success('注册成功')
    await router.push('/')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '注册失败')
  } finally {
    submitting.value = false
  }
}
</script>
